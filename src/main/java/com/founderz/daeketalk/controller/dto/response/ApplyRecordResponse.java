package com.founderz.daeketalk.controller.dto.response;

public record ApplyRecordResponse(
        String name,
        Integer generation,
        String phone_number
) {
}