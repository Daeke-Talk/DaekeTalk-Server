package com.founderz.daektalk.service;

import com.founderz.daektalk.controller.dto.request.ApplyRequest;
import com.founderz.daektalk.controller.dto.request.PhoneNumbersRequest;
import com.founderz.daektalk.controller.dto.response.ApplyRecordResponse;
import com.founderz.daektalk.entity.Participant;
import com.founderz.daektalk.repository.ParticipantRepository;
import com.founderz.daektalk.service.component.DaekTalkNotifier;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipantRepository participantRepository;
    private final DaekTalkNotifier DaekTalkNotifier;

    @Transactional
    public void applyForDaekTalk(ApplyRequest request) {
        Participant participant = Participant.builder()
                .name(request.name())
                .generation(request.generation())
                .jobPosition(request.job_position())
                .phoneNumber(request.phone_number())
                .isNetworkingParticipant(request.is_networking_participant())
                .isConfirmed(false)
                .build();

        participantRepository.save(participant);
        DaekTalkNotifier.notifyApplied(participant);
    }

    @Transactional
    public void confirmedParticipation(PhoneNumbersRequest request) {
        List<Participant> participants = participantRepository.findAllByPhoneNumberIn(request.phone_numbers());
        for (Participant participant : participants) {
            participant.confirm();
        }
        participants.forEach(DaekTalkNotifier::notifyConfirmed);
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
        DaekTalkNotifier.notifyCancelled(phoneNumber);
    }
}