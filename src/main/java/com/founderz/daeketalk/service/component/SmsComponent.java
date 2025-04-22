package com.founderz.daeketalk.service.component;

import com.founderz.daeketalk.sms.SmsClient;
import com.founderz.daeketalk.sms.SmsProperties;
import com.founderz.daeketalk.sms.dto.SendSmsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsComponent {

    private final SmsProperties smsProperties;
    private final SmsClient smsClient;

    public void sendCode(String phoneNumber, int validationCode) {
        SendSmsRequest request = new SendSmsRequest(
                smsProperties.tokenKey(),
                "sms",
                smsProperties.phoneNumber(),
                phoneNumber,
                createMessage(validationCode)
        );

        smsClient.sendSms(smsProperties.apiKey(), "111.222.111.222", request);
    }

    private String createMessage(int validationCode) {
        return String.format("""
            대크톡 인증번호 [%06d]
            타인에게 절대 알리지 마세요.""", validationCode).stripIndent();
    }
}
