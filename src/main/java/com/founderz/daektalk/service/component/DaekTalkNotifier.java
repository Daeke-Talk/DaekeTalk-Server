package com.founderz.daektalk.service.component;

import com.founderz.daektalk.entity.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DaekTalkNotifier {

    private final SmsSender smsSender;

    // 코드 발송 알림
    public void notifyCode(String phoneNumber, int validationCode) {
        smsSender.sendCode(phoneNumber, validationCode);
    }

    // 참가 신청 알림
    public void notifyApplied(Participant participant) {
        smsSender.sendCheckedMessage(participant.getPhoneNumber(), participant.getName());
    }

    // 참가 확정 알림
    public void notifyConfirmed(Participant participant) {
        smsSender.sendConfirmedMessage(participant.getPhoneNumber(), participant.getName());
    }

    // 참가 취소 알림
    public void notifyCancelled(String phoneNumber) {
        smsSender.sendCancelledMessage(phoneNumber);
    }
}