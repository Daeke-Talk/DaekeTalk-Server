package com.founderz.daeketalk.repository;

import com.founderz.daeketalk.entity.PhoneValidationCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneValCheckRepository extends JpaRepository<PhoneValidationCheck, String> {
}
