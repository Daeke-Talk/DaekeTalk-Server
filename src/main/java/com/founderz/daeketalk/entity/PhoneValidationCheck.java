package com.founderz.daeketalk.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "validation-code", timeToLive = 300)
@AllArgsConstructor
public class PhoneValidationCheck {

    @Id
    private String phoneNumber;
    private final String name;
    private final int validationCode;
    private boolean isValid;

    public boolean isValid() {
        return isValid;
    }

    public void validationsucceed() {
        this.isValid = true;
    }
}