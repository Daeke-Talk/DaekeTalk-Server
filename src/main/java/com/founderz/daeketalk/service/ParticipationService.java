package com.founderz.daeketalk.service;

import com.founderz.daeketalk.controller.dto.request.ApplyRequest;
import com.founderz.daeketalk.controller.dto.request.PhoneNumbersRequest;
import com.founderz.daeketalk.controller.dto.response.ApplyRecordResponse;
import com.founderz.daeketalk.entity.Participant;
import com.founderz.daeketalk.repository.ParticipantRepository;
import com.founderz.daeketalk.service.component.DaekeTalkNotifier;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipantRepository participantRepository;
    private final DaekeTalkNotifier daekeTalkNotifier;

    @Transactional
    public void applyForDaekeTalk(ApplyRequest request) {
        Participant participant = Participant.builder()
                .name(request.name())
                .generation(request.generation())
                .jobPosition(request.job_position())
                .phoneNumber(request.phone_number())
                .isConfirmed(false)
                .build();

        participantRepository.save(participant);
        daekeTalkNotifier.notifyApplied(participant);
    }

    @Transactional
    public void confirmedParticipation(PhoneNumbersRequest request) {
        List<Participant> participants = participantRepository.findAllByPhoneNumberIn(request.phone_numbers());
        for (Participant participant : participants) {
            participant.confirm();
        }
        participants.forEach(daekeTalkNotifier::notifyConfirmed);
    }

    @Transactional(readOnly = true)
    public ApplyRecordResponse getApplyRecord(String phoneNumber) {
        return participantRepository.findByPhoneNumber(phoneNumber)
                .map(participant -> new ApplyRecordResponse(
                        participant.getName(),
                        participant.getGeneration(),
                        participant.getPhoneNumber()))
                .orElseThrow(() -> new EntityNotFoundException("해당 전화번호로 참가자를 찾을 수 없습니다."));
    }

    @Transactional
    public void cancelApplication(String phoneNumber) {
        participantRepository.deleteByPhoneNumber(phoneNumber);
        daekeTalkNotifier.notifyCancelled(phoneNumber);
    }
}