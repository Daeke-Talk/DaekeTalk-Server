package com.founderz.daektalk.service.component;

import com.founderz.daektalk.entity.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.founderz.daektalk.repository.ParticipantRepository;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.StringBuilder;

@Component
@RequiredArgsConstructor
public class DaekTalkNotifier {

    private final SmsSender smsSender;
    private final ParticipantRepository participantRepository;
    private final WebClient webClient = WebClient.create();
    private final String discordWebhookUrl = "https://discord.com/api/webhooks/your_webhook_url"; // TODO: 환경변수로 분리

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
        // 기수별 신청자 수 집계
        Map<Integer, Long> countByGeneration = participantRepository.findAll().stream()
            .collect(Collectors.groupingBy(Participant::getGeneration, Collectors.counting()));
        StringBuilder genCountMsg = new StringBuilder();
        countByGeneration.forEach((gen, cnt) -> genCountMsg.append(gen).append("기: ").append(cnt).append("명\n"));
        String content = String.format(
            "[%s] %s (%s기, %s)\n\n기수별 신청자 수:\n%s",
            action,
            participant.getName(),
            participant.getGeneration(),
            participant.getJobPosition(),
            genCountMsg
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