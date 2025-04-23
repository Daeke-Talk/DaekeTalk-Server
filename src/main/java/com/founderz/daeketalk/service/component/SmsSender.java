package com.founderz.daeketalk.service.component;

import com.founderz.daeketalk.sms.SmsClient;
import com.founderz.daeketalk.sms.SmsProperties;
import com.founderz.daeketalk.sms.dto.SendSmsForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsSender {

    private final SmsProperties smsProperties;
    private final SmsClient smsClient;
    private final SmsMessageFactory messageFactory;

    private void send(String phoneNumber, String message) {
        SendSmsForm request = new SendSmsForm(
                smsProperties.tokenKey(),
                "sms",
                smsProperties.phoneNumber(),
                phoneNumber,
                message
        );

        smsClient.sendSms(smsProperties.apiKey(), "111.222.111.222", request);
    }

    public void sendCode(String phoneNumber, int validationCode) {
        send(phoneNumber, messageFactory.createCodeMessage(validationCode));
    }

    public void sendCheckedMessage(String phoneNumber, String name) {
        send(phoneNumber, messageFactory.createCheckMessage(name));
    }

    public void sendConfirmedMessage(String phoneNumber, String name) {
        send(phoneNumber, messageFactory.createConfirmedMessage(name));
    }

    public void sendCancelledMessage(String phoneNumber) {
        send(phoneNumber, messageFactory.createCancelMessage());
    }
}