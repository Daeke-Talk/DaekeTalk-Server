package com.founderz.daeketalk.controller;

import com.founderz.daeketalk.controller.dto.request.ApplyRequest;
import com.founderz.daeketalk.controller.dto.request.PhoneNumbersRequest;
import com.founderz.daeketalk.controller.dto.response.ApplyRecordResponse;
import com.founderz.daeketalk.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DaekeTalkParticipationController {

    private final ParticipationService participationService;

    @PostMapping("/apply")
    public void applyDaekeTalk(@RequestBody ApplyRequest request) {
        participationService.applyForDaekeTalk(request);
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
