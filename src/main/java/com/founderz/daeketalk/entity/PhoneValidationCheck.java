package com.founderz.daeketalk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "validation-code", timeToLive = 300)
@AllArgsConstructor
public class PhoneValidationCheck {

    @Id
    private String phoneNumber;
    private final int validationCode;
    private boolean isValid;

    public void validationSucceed() {
        this.isValid = true;
    }
}