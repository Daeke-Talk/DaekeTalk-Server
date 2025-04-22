package com.founderz.daeketalk.service.component;

import com.founderz.daeketalk.sms.SmsClient;
import com.founderz.daeketalk.sms.SmsProperties;
import com.founderz.daeketalk.sms.dto.SendSmsForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsComponent {

    private final SmsProperties smsProperties;
    private final SmsClient smsClient;

    public void sendConfirmedMessage(String phoneNumber, String name) {
        SendSmsForm request = new SendSmsForm(
                smsProperties.tokenKey(),
                "sms",
                smsProperties.phoneNumber(),
                phoneNumber,
                createConfirmedMessage(name)
        );

        smsClient.sendSms(smsProperties.apiKey(), "111.222.111.222", request);
    }

    public void sendCode(String phoneNumber, int validationCode) {
        SendSmsForm request = new SendSmsForm(
                smsProperties.tokenKey(),
                "sms",
                smsProperties.phoneNumber(),
                phoneNumber,
                createCodeMessage(validationCode)
        );

        smsClient.sendSms(smsProperties.apiKey(), "111.222.111.222", request);
    }

    private String createCodeMessage(int validationCode) {
        return String.format("""
            대크톡 인증번호 [%06d]
            타인에게 절대 알리지 마세요.""", validationCode).stripIndent();
    }

    private String createConfirmedMessage(String name) {
        return String.format("""
                [대크톡]
                
                안녕하세요, %s님
                DSM 9기 학생회입니다.
                
                대크톡 참가 신청이 정상적으로 완료되었습니다.
                
                참가 확정 여부는 추후 해당 번호를 통해서 공지해드릴 예정입니다.
                
                문의 사항 & 질문
                9기 학생회장 변정현
                010-5706-2562 / 디스코드""", name).stripIndent();
    }
}
