package com.founderz.daeketalk.controller.dto.request;

public record CodeValidationRequest(
        String phone_number,
        String validation_code
) {
}
