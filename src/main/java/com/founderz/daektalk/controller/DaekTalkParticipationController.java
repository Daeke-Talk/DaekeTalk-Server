package com.founderz.daektalk.controller;

import com.founderz.daektalk.controller.dto.request.ApplyRequest;
import com.founderz.daektalk.controller.dto.request.PhoneNumbersRequest;
import com.founderz.daektalk.controller.dto.response.ApplyRecordResponse;
import com.founderz.daektalk.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DaekTalkParticipationController {

    private final ParticipationService participationService;

    @PostMapping("/apply")
    public void applyDaekTalk(@RequestBody ApplyRequest request) {
        participationService.applyForDaekTalk(request);
    }

    @PostMapping("/confirmed")
    public void confirmedParticipation(@RequestBody PhoneNumbersRequest request) {
        participationService.confirmedParticipation(request);
    }

    @GetMapping("/record/{phoneNumber}")
    public ApplyRecordResponse getApplyRecord(@PathVariable String phoneNumber) {
        return participationService.getApplyRecord(phoneNumber);
    }

    @DeleteMapping("/cancel/{phoneNumber}")
    public void cancelApply(@PathVariable String phoneNumber) {
        participationService.cancelApplication(phoneNumber);
    }
}
