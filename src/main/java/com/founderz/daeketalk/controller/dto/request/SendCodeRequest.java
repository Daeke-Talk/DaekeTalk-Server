package com.founderz.daeketalk.controller.dto.request;

public record SendCodeRequest(
        String name,
        String phone_number
) {
}
