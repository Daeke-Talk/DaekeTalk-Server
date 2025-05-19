package com.founderz.daektalk.service.component;

import com.founderz.daektalk.entity.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.founderz.daektalk.repository.ParticipantRepository;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.Comparator;
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
        // 기수별 신청자 명단 집계
        Map<Integer, List<Participant>> byGen = participantRepository.findAll().stream()
            .collect(Collectors.groupingBy(Participant::getGeneration));

        StringBuilder genMsg = new StringBuilder();
        byGen.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                Integer gen = entry.getKey();
                List<Participant> list = entry.getValue();
                genMsg.append(gen).append("기: ").append(list.size()).append("명\n");
                // 분야별 그룹핑
                Map<JobPosition, List<Participant>> byJob = list.stream()
                    .collect(Collectors.groupingBy(Participant::getJobPosition));
                byJob.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.comparing(Enum::name)))
                    .forEach(jobEntry -> {
                        JobPosition job = jobEntry.getKey();
                        List<Participant> jobList = jobEntry.getValue();
                        String names = jobList.stream()
                            .sorted(Comparator.comparing(Participant::getName))
                            .map(p -> EXECUTIVES.contains(p.getName()) ? p.getName() + "(임원)" : p.getName())
                            .collect(Collectors.joining(", "));
                        genMsg.append("- ").append(job.name()).append(": ").append(names).append("\n");
                    });
            });

        String content = String.format(
            "[%s] %s (%s기, %s)\n\n기수별 신청자 명단:\n%s",
            action,
            participant.getName(),
            participant.getGeneration(),
            participant.getJobPosition(),
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