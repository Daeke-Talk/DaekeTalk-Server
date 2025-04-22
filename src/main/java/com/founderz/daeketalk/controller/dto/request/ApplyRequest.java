package com.founderz.daeketalk.controller.dto.request;

import com.founderz.daeketalk.entity.JobPosition;

public record ApplyRequest(
        String name,
        Integer generation,
        JobPosition job_position,
        String phone_number,
        boolean is_presenter,
        String presentation_topic
) {
}
