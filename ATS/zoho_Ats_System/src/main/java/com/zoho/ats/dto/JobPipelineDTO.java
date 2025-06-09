package com.zoho.ats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class JobPipelineDTO {
//	public JobPipelineDTO(String jobTitle) {
//		// TODO Auto-generated constructor stub
//		this.jobTitle=jobTitle;
//	}
	public String jobTitle;
	private Long totalApplications;
	private Long matchedCandidates;
	private Long interviews;
	private Long offers;
	private Long hired;
	private Long rejected;
	public JobPipelineDTO(String jobTitle, Long totalApplications, Long matchedCandidates, Long interviews, Long offers,
			Long hired, Long rejected) {
		super();
		this.jobTitle = jobTitle;
		this.totalApplications = totalApplications;
		this.matchedCandidates = matchedCandidates;
		this.interviews = interviews;
		this.offers = offers;
		this.hired = hired;
		this.rejected = rejected;
	}
	
}
