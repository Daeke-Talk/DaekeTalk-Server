package com.founderz.daeketalk.controller;

import com.founderz.daeketalk.controller.dto.request.ApplyRequest;
import com.founderz.daeketalk.controller.dto.request.PhoneNumbersRequest;
import com.founderz.daeketalk.controller.dto.response.ApplyRecordResponse;
import com.founderz.daeketalk.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apply")
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping()
    public void applyDaekeTalk(@RequestBody ApplyRequest request) {
        applyService.applyDaekeTalk(request);
    }

    @PostMapping("/confirmed")
    public void confirmedParticipation(@RequestBody PhoneNumbersRequest request) {
        applyService.confirmedParticipation(request);
    }

    @GetMapping("/record/{phoneNumber}")
    public ApplyRecordResponse getApplyRecord(@PathVariable String phoneNumber) {
        return applyService.getApplyRecord(phoneNumber);
    }

    @DeleteMapping("/cancel/{phoneNumber}")
    public void cancelApply(@PathVariable String phoneNumber) {
        applyService.cancelApply(phoneNumber);
    }
}
