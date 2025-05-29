package com.ats.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Service;

import com.ats.dto.ApplicationDTO;
import com.ats.dto.CandidateMatchDTO;
import com.ats.entity.Application;
import com.ats.entity.Candidate;
import com.ats.entity.Job;
import com.ats.enums.ApplicationStatus;
import com.ats.repository.ApplicationRepository;
import com.ats.repository.CandidateRepository;
import com.ats.repository.JobRepository;

@Service
public class ApplicationService {
	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private JobRepository jobRepository;


	//create application status
	public Application applyForJob(Long candidateId, Long jobId) {
		Candidate candidate = candidateRepository.findById(candidateId)
				.orElseThrow(() -> new NoSuchElementException("Candidate not found"));

		Job job = jobRepository.findById(jobId)
				.orElseThrow(() -> new NoSuchElementException("Job not found"));

		Application application = new Application();
		application.setCandidate(candidate);
		application.setJob(job);
		application.setStatus(ApplicationStatus.APPLIED);
		//        application.setAppliedDate(LocalDateTime.now());

		return applicationRepository.save(application);
	}
	// get application status
	public ApplicationStatus getStatus(Long candidateId, Long jobId) {
		return applicationRepository.findByCandidateId(candidateId).stream()
				.filter(app -> app.getJob().getId().equals(jobId))
				.findFirst()
				.orElseThrow(() -> new NoSuchElementException("Application not found"))
				.getStatus();
	}

	// updating application status 
	public Application updateApplicationStatus(Long applicationId, ApplicationStatus newStatus) {
		Application application = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new NoSuchElementException("Application not found with ID: " + applicationId));

		application.setStatus(newStatus);

		Candidate candidate=application.getCandidate();// output is candidate obj
		candidate.setStatus(newStatus); // updating status in candidate entity/table
		candidateRepository.save(candidate);

		return applicationRepository.save(application);
	}
	//DTO class object
	public List<ApplicationDTO> getMinimalApplications() {
		List<Application> apps = applicationRepository.findAll();
		return apps.stream().map(app -> {
			ApplicationDTO dto = new ApplicationDTO();
			dto.setApp_id(app.getId());
			dto.setStatus(app.getStatus());
			dto.setCandidateId(app.getCandidate().getId());
			dto.setJobId(app.getJob().getId());
			return dto;
		}).toList();
	}

	// checking matching skills between jobId and candidate applied
	public List<CandidateMatchDTO> getMatchingCandidateEmails(long jobId){
		Job job =jobRepository.findById(jobId)   // finding job object and stores object in job
				.orElseThrow(()->new RuntimeException("JobId is not found"));
		List<Application> applications=applicationRepository.findByJobId(jobId);
		String[] requiredSkills=job.getSkills().toLowerCase().split(",");
		Set<String> requiredSkillSet=Arrays.stream(requiredSkills).map(String::trim).collect(Collectors.toSet());

		List<CandidateMatchDTO> details= new ArrayList();


		for (Application app : applications) {
			Candidate candidate = app.getCandidate();

			String[] candidateSkills = candidate.getSkills().toLowerCase().split(","); 
			Set<String> candidateSkillSet = Arrays.stream(candidateSkills)
					.map(String::trim)
					.collect(Collectors.toSet());

			// Check if candidate has at least one matching skill
			for (String skill : candidateSkillSet) {
				if (requiredSkillSet.contains(skill)) {
					//pushing data to candidateMatchDTO
					CandidateMatchDTO dto= new CandidateMatchDTO(
							candidate.getFirstName(),
							candidate.getSkills(),
							candidate.getEmail()
							);

					details.add(dto);
					break;
				}
			}

		}
		return details;

	}



}
