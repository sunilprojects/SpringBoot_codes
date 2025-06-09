package com.zoho.ats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
public class CandidateDTO { // this is used to hide resume and and shor necessary fileds

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String location;
	private String skills;
	//    private Long jobId;



}
