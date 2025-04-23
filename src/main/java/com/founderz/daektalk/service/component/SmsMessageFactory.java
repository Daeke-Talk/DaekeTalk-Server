package com.founderz.daektalk.service.component;

import org.springframework.stereotype.Component;

@Component
public class SmsMessageFactory {

    public String createCodeMessage(int validationCode) {
        return String.format("""
            대크톡 인증번호 [%06d]
            타인에게 절대 알리지 마세요.""", validationCode).stripIndent();
    }

    public String createCheckMessage(String name) {
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

    public String createConfirmedMessage(String name) {
        return String.format("""
            [대크톡]

            안녕하세요, %s님
            DSM 9기 학생회입니다.

            대크톡 참가가 확정되었음을 안내드립니다!
            행사는 아래와 같이 진행될 예정입니다:

            장소: 마루 180(서울 강남구 역삼로 180)
            날짜: 2025년 6월 14일 (토)
            시간: 10:00 ~ 17:00

            입장은 09시 30분부터 가능합니다.
            시간에 맞춰 여유 있게 도착해 주세요!

            멋진 시간 함께 만들어가요 :)
            감사합니다!

            행사 소개 페이지:
            https://daektalk.xquare.app/""", name).stripIndent();
    }

    public String createCancelMessage() {
        return """
            [대크톡]

            요청하신 대크톡 참가 신청 취소가 정상적으로 처리되었습니다.

            남겨주신 관심에 진심으로 감사드리며,
            다음 기회에 다시 뵐 수 있기를 바랍니다.

            감사합니다.
            DSM 9기 학생회 드림""".stripIndent();
    }
}