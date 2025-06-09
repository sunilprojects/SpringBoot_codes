package com.zoho.ats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecruiterSummaryDTO {
	  private String recruiterName;
	    private String officialEmail;
	    private String companyName;
	    private Long totalJobsPosted;

}
