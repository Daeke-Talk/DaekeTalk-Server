package com.founderz.daektalk.repository;

import com.founderz.daektalk.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByPhoneNumber(String phoneNumber);
    void deleteByPhoneNumber(String phoneNumber);
    List<Participant> findAllByPhoneNumberIn(List<String> phoneNumbers);
}
