package com.ats.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class MatchedCandidates {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
//	
//	@JoinColumn(name="job_id")
//	private Job job;
//	
//	@JoinColumn(name="candidate_id")
//	private Candidate candidate;
//	
//	private Integer matchScore;
//	
//	private String matchedSkills;
//	
//	private LocalDate date;
//	

}
