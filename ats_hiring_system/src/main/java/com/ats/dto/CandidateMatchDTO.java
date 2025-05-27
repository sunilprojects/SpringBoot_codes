package com.ats.dto;

import lombok.Data;

@Data
public class CandidateMatchDTO { 

	private String firstName;
	private String skills;
	private String email;
	public CandidateMatchDTO(String firstName, String skills, String email) {
		super();
		this.firstName = firstName;
		this.skills = skills;
		this.email = email;
	}

}
