package com.founderz.daeketalk.sms.dto;

public record SendSmsForm(
        String token_key,
        String msg_type,
        String send_phone,
        String dest_phone,
        String msg_body
) {
}
