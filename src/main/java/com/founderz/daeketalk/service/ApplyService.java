package com.founderz.daeketalk.service;

import com.founderz.daeketalk.controller.dto.ApplyRequest;
import com.founderz.daeketalk.entity.Participant;
import com.founderz.daeketalk.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DaekeTalkService {

    private final ParticipantRepository participantRepository;

    @Transactional
    public void applyDaekeTalk(ApplyRequest request) {
        participantRepository.save(
                Participant.builder()
                        .name(request.name())
                        .generation(request.generation())
                        .jobPosition(request.job_position())
                        .phoneNumber(request.phone_number())
                        .isPresenter(request.is_presenter())
                        .presentationTopic(request.presentation_topic())
                        .build());
    }
}