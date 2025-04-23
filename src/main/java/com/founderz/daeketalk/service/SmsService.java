package com.founderz.daeketalk.service;

import com.founderz.daeketalk.controller.dto.request.CodeValidationRequest;
import com.founderz.daeketalk.controller.dto.request.SendCodeRequest;
import com.founderz.daeketalk.entity.PhoneValidationCheck;
import com.founderz.daeketalk.repository.PhoneValCheckRepository;
import com.founderz.daeketalk.service.component.SmsComponent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsComponent smsComponent;
    private final PhoneValCheckRepository phoneValCheckRepository;

    @Transactional
    public void sendCode(SendCodeRequest request) {
        int validationCode = (int)(Math.random() * 900000) + 100000; // 100000 ~ 999999

        phoneValCheckRepository.save(
                new PhoneValidationCheck(
                        request.phone_number(),
                        validationCode,
                        false
                ));

        smsComponent.sendCode(request.phone_number(), validationCode);
    }

    @Transactional
    public void validationCode(CodeValidationRequest request) {
        PhoneValidationCheck phoneValidationCheck = phoneValCheckRepository.findById(request.phone_number())
                .orElseThrow(() -> new EntityNotFoundException("해당 전화번호로 참가자를 찾을 수 없습니다."));

        if (phoneValidationCheck.getValidationCode() == request.validation_code()) {
            phoneValidationCheck.validationSucceed();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다.");
        }

        phoneValCheckRepository.save(phoneValidationCheck);
    }
}
