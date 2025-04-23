package com.founderz.daektalk.controller.dto.request;

public record CodeValidationRequest(
        String phone_number,
        Integer validation_code
) {
}
