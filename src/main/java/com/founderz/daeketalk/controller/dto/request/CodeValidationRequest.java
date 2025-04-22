package com.founderz.daeketalk.controller.dto.request;

public record CodeValidationRequest(
        String phone_number,
        Integer validation_code
) {
}
