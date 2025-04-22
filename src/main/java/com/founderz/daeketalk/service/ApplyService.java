package com.founderz.daeketalk.service;

import com.founderz.daeketalk.controller.dto.request.ApplyRequest;
import com.founderz.daeketalk.controller.dto.response.ApplyRecordResponse;
import com.founderz.daeketalk.entity.Participant;
import com.founderz.daeketalk.repository.ParticipantRepository;
import com.founderz.daeketalk.service.component.SmsComponent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ParticipantRepository participantRepository;
    private final SmsComponent smsComponent;

    @Transactional
    public void applyDaekeTalk(ApplyRequest request) {
        participantRepository.save(
                Participant.builder()
                        .name(request.name())
                        .generation(request.generation())
                        .jobPosition(request.job_position())
                        .phoneNumber(request.phone_number())
                        .build());

        smsComponent.sendConfirmedMessage(request.phone_number(), request.name());
    }

    @Transactional(readOnly = true)
    public ApplyRecordResponse getApplyRecord(String phoneNumber) {
        Participant participant = participantRepository.findByPhoneNumber(phoneNumber);

        if (participant == null) {
            throw new EntityNotFoundException("해당 전화번호로 참가자를 찾을 수 없습니다.");
        }

        return new ApplyRecordResponse(
                participant.getName(),
                participant.getGeneration(),
                participant.getPhoneNumber()
        );
    }

    @Transactional
    public void cancelApply(String phoneNumber) {
        participantRepository.deleteByPhoneNumber(phoneNumber);
    }
}