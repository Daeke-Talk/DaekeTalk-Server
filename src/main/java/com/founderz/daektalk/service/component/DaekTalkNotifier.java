package com.founderz.daektalk.service.component;

import com.founderz.daektalk.entity.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.founderz.daektalk.repository.ParticipantRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Comparator;
import java.util.stream.Collectors;
import com.founderz.daektalk.entity.JobPosition;

@Component
@RequiredArgsConstructor
public class DaekTalkNotifier {

    private final SmsSender smsSender;
    private final ParticipantRepository participantRepository;
    private final WebClient webClient = WebClient.create();
    private final String discordWebhookUrl = "https://discord.com/api/webhooks/1372039931425456219/CB3ICc_UmzpSOG6UI1BRzQ09hvsgipoNQ_yECEGDhzUz4yavqVWdl5cRz6F1YdnAIcS2"; // TODO: 환경변수로 분리

    private static final Set<String> EXECUTIVES = Set.of(
        "부현수", "이선우", "이지후", "김정욱", "박의엘", "김소림", "민수아", "박태수", "변정현", "신주희", "오혜민", "유서희", "이은호", "이다연", "이해나", "임한성", "정일웅"
    );

    // 코드 발송 알림
    public void notifyCode(String phoneNumber, int validationCode) {
        smsSender.sendCode(phoneNumber, validationCode);
    }

    // 참가 신청 알림
    public void notifyApplied(Participant participant) {
        smsSender.sendCheckedMessage(participant.getPhoneNumber(), participant.getName());
        sendDiscordNotification("신청", participant);
    }

    // 참가 확정 알림
    public void notifyConfirmed(Participant participant) {
        smsSender.sendConfirmedMessage(participant.getPhoneNumber(), participant.getName());
    }

    // 참가 취소 알림
    public void notifyCancelled(Participant participant) {
        smsSender.sendCancelledMessage(participant.getPhoneNumber());
        sendDiscordNotification("취소", participant);
    }

    private void sendDiscordNotification(String action, Participant participant) {
        List<Participant> all = participantRepository.findAll();
        List<Participant> executives = all.stream()
            .filter(p -> EXECUTIVES.contains(p.getName()))
            .sorted(Comparator.comparing(Participant::getName))
            .toList();
        List<Participant> nonExecutives = all.stream()
            .filter(p -> !EXECUTIVES.contains(p.getName()))
            .toList();

        long total = nonExecutives.size();
        long graduates = nonExecutives.stream().filter(p -> p.getGeneration() >= 8).count();
        long students = nonExecutives.stream().filter(p -> p.getGeneration() < 8).count();

        StringBuilder execMsg = new StringBuilder();
        execMsg.append("학생회(").append(executives.size()).append("명):\n");
        execMsg.append(executives.stream().map(Participant::getName).collect(Collectors.joining(", "))).append("\n\n");

        // 기수별 명단 (임원 제외)
        Map<Integer, List<Participant>> byGen = nonExecutives.stream()
            .collect(Collectors.groupingBy(Participant::getGeneration));
        StringBuilder genMsg = new StringBuilder();
        byGen.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                Integer gen = entry.getKey();
                List<Participant> list = entry.getValue();
                genMsg.append(gen).append("기: ").append(list.size()).append("명\n");
                Map<JobPosition, List<Participant>> byJob = list.stream()
                    .collect(Collectors.groupingBy(Participant::getJobPosition));
                byJob.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.comparing(Enum::name)))
                    .forEach(jobEntry -> {
                        JobPosition job = jobEntry.getKey();
                        List<Participant> jobList = jobEntry.getValue();
                        String names = jobList.stream()
                            .sorted(Comparator.comparing(Participant::getName))
                            .map(Participant::getName)
                            .collect(Collectors.joining(", "));
                        genMsg.append("- ").append(job.name()).append(": ").append(names).append("\n");
                    });
            });

        String content = String.format(
            "[%s] %s (%s기, %s)\n\n전체 인원: %d명 (학생회 임원 제외)\졸업생: %d명 / 재학생: %d명\n\n%s기수별 신청자 명단:\n%s",
            action,
            participant.getName(),
            participant.getGeneration(),
            participant.getJobPosition(),
            total,
            students,
            graduates,
            execMsg,
            genMsg
        );
        webClient.post()
            .uri(discordWebhookUrl)
            .header("Content-Type", "application/json")
            .bodyValue("{\"content\": \"" + content.replace("\n", "\\n") + "\"}")
            .retrieve()
            .bodyToMono(String.class)
            .subscribe();
    }
}