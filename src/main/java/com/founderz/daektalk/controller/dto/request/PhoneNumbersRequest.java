package com.founderz.daektalk.controller.dto.request;

import java.util.List;

public record PhoneNumbersRequest(
        List<String> phone_numbers
) {
}
