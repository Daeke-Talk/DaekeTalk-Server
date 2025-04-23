package com.founderz.daeketalk.controller;

import com.founderz.daeketalk.controller.dto.request.CodeValidationRequest;
import com.founderz.daeketalk.controller.dto.request.SendCodeRequest;
import com.founderz.daeketalk.service.SmsValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SmsValidationController {

    private final SmsValidationService smsValidationService;

    @PostMapping("/send-code")
    public void sendCode(@RequestBody SendCodeRequest request) {
        smsValidationService.sendCode(request);
    }

    @PostMapping("/validation-code")
    public void validationCode(@RequestBody CodeValidationRequest request) {
        smsValidationService.validationCode(request);
    }
}
