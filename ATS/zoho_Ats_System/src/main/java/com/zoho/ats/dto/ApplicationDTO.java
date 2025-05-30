package com.zoho.ats.dto;

import com.zoho.ats.enums.ApplicationStatus;

import lombok.Data;
@Data
public class ApplicationDTO { // to show only details present application table
    private Long app_id;
    private ApplicationStatus status;
    private Long candidateId;
    private Long jobId;
}
