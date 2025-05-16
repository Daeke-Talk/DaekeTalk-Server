package com.founderz.daektalk.controller.dto.request;

import com.founderz.daektalk.entity.JobPosition;

public record ApplyRequest(
        String name,
        Integer generation,
        JobPosition job_position,
        String phone_number,
        boolean is_networking_participant
) {
}
