package com.ats.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ats.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne 
	@JoinColumn(name="job_id", nullable = false)
	@JsonIgnoreProperties({"applications", "candidates", "hibernateLazyInitializer", "handler"})
	private Job job;
	
	@ManyToOne
	@JoinColumn(name="candidate_id", nullable=false)
	@JsonIgnoreProperties({"applications", "job", "hibernateLazyInitializer", "handler"})
	private Candidate candidate;

	
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime appliedDate;
	

	
	// @Data is used above for setter injections of candidate and job as well application

}
