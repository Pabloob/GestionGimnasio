package com.gymmanagement.backend.dto.put;

import lombok.Data;

@Data
public class EnrollmentPutDTO {
    private Long customerId;
    private Long classId;
    private boolean attended = false;
}
