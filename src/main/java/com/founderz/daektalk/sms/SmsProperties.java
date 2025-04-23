package com.founderz.daektalk.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms")
public record SmsProperties(
        String apiKey,
        String tokenKey,
        String phoneNumber
) {
}
