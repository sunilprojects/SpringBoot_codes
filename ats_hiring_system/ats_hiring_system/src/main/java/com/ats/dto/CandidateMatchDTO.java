package com.ats.dto;

import lombok.Data;

@Data
public class CandidateMatchDTO {   // to dispaly the candidates whose skills are matched

	private String firstName;
	private String skills;
	private String email;
	// we can add fields to display
	
	
	public CandidateMatchDTO(String firstName, String skills, String email) {
		super();
		this.firstName = firstName;
		this.skills = skills;
		this.email = email;
	}

}
