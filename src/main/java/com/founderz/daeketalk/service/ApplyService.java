package com.founderz.daeketalk.service;

import com.founderz.daeketalk.controller.dto.request.ApplyRequest;
import com.founderz.daeketalk.controller.dto.request.PhoneNumbersRequest;
import com.founderz.daeketalk.controller.dto.response.ApplyRecordResponse;
import com.founderz.daeketalk.entity.Participant;
import com.founderz.daeketalk.repository.ParticipantRepository;
import com.founderz.daeketalk.service.component.SmsComponent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                        .isConfirmed(false)
                        .build());

        smsComponent.sendCheckedMessage(request.phone_number(), request.name());
    }

    @Transactional
    public void confirmedParticipation(PhoneNumbersRequest request) {
        List<Participant> participants = participantRepository.findAllByPhoneNumberIn(request.phone_numbers());

        for (Participant participant : participants) {
            participant.confirm();
            smsComponent.sendConfirmedMessage(participant.getPhoneNumber(), participant.getName());
        }
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

        smsComponent.sendCancelledMessage(phoneNumber);
    }
}