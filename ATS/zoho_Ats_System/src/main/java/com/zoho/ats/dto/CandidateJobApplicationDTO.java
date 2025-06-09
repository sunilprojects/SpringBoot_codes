package com.zoho.ats.dto;

import java.time.LocalDateTime;

import com.zoho.ats.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateJobApplicationDTO {
    private String jobTitle;
    private String companyName; // if you have it
    private LocalDateTime appliedDate;
    private ApplicationStatus status;
}
