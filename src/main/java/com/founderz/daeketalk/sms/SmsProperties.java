package com.founderz.daeketalk.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms")
public record SmsProperties(
        String apiKey,
        String tokenKey,
        String phoneNumber
) {
}
