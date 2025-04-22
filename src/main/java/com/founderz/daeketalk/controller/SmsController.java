package com.founderz.daeketalk.controller;

import com.founderz.daeketalk.controller.dto.request.ApplyRequest;
import com.founderz.daeketalk.controller.dto.request.PhoneRequest;
import com.founderz.daeketalk.controller.dto.response.ApplyRecordResponse;
import com.founderz.daeketalk.service.ApplyService;
import com.founderz.daeketalk.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/DaekeTalk")
public class SmsController {

    private final ApplyService applyService;
    private final SmsService smsService;

    @PostMapping("/apply")
    public void applyDaekeTalk(@RequestBody ApplyRequest request) {
        applyService.applyDaekeTalk(request);
    }

    @GetMapping("/apply-record/{phoneNumber}")
    public ApplyRecordResponse getApplyRecord(@PathVariable String phoneNumber) {
        return applyService.getApplyRecord(phoneNumber);
    }

    @DeleteMapping("/apply-cancel")

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody PhoneRequest request) {
        smsService.sendMessage(request.phone_number());
    }
}
