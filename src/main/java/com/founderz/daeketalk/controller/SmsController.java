package com.founderz.daeketalk.controller;

import com.founderz.daeketalk.controller.dto.request.CodeValidationRequest;
import com.founderz.daeketalk.controller.dto.request.SendCodeRequest;
import com.founderz.daeketalk.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send-code")
    public void sendCode(@RequestBody SendCodeRequest request) {
        smsService.sendCode(request);
    }

    @PostMapping("/validation-code")
    public void validationCode(@RequestBody CodeValidationRequest request) {
        smsService.validationCode(request);
    }
}
