package com.ats.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Data
public class CandidateDTO { // this is used to hide resume and and shor necessary fileds
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String location;
	private String skills;
//    private Long jobId;
	public CandidateDTO(Long id, String firstName, String lastName, String email,
            String phoneNumber, String location, String skills) {
this.id = id;
this.firstName = firstName;
this.lastName = lastName;
this.email = email;
this.phoneNumber = phoneNumber;
this.location = location;
this.skills = skills;
}

}
