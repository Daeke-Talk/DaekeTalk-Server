package com.founderz.daeketalk.repository;

import com.founderz.daeketalk.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Participant findByPhoneNumber(String phoneNumber);
    void deleteByPhoneNumber(String phoneNumber);
    List<Participant> findAllByPhoneNumberIn(List<String> phoneNumbers);
}
