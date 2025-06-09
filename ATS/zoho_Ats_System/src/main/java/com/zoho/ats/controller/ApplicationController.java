package com.zoho.ats.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zoho.ats.dto.ApplicationDTO;
import com.zoho.ats.dto.CandidateMatchDTO;
import com.zoho.ats.dto.JobPipelineDTO;
import com.zoho.ats.entity.Application;
import com.zoho.ats.enums.ApplicationStatus;
import com.zoho.ats.repository.ApplicationRepository;
import com.zoho.ats.service.ApplicationService;
import com.zoho.ats.service.JobPipelineService;

@RestController
@RequestMapping("api/applications")
public class ApplicationController {
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ApplicationRepository applicationRepository;
    @Autowired
	private JobPipelineService jobPipelineService;

	@GetMapping("/all-status")
	public List<Application> findStatus() {
		return applicationRepository.findAll();
	}

	@GetMapping("/status")
	public ApplicationStatus getStatus(@RequestParam Long candidateId, @RequestParam Long jobId) {
		return applicationService.getStatus(candidateId, jobId);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<String> updateStatus(@RequestParam Long id, @RequestParam ApplicationStatus status) {
		Application updatedApp = applicationService.updateApplicationStatus(id, status);
		return ResponseEntity.ok("Application Status Updated");
	}

	@GetMapping("/minimal")
	public List<ApplicationDTO> getMinimalApplications() {
		return applicationService.getMinimalApplications();
	}

	@GetMapping("/all-emails/{jobId}")  //skills matching candidate detais
	public ResponseEntity<List<CandidateMatchDTO>>  getMatchingEmails(@PathVariable Long jobId){
		List<CandidateMatchDTO> candidateDetails= applicationService.getMatchingCandidateEmails(jobId);
		return ResponseEntity.ok(candidateDetails);
	}
	
	@GetMapping("/dashboard") // to get full dashboard
	 public List<JobPipelineDTO> getapplicationStatus(){
		 return jobPipelineService.getapplicationStatus();
	 }
	
	@GetMapping("/job-role") // to get rolebased dashboard
	public List<JobPipelineDTO> getJobRole(@RequestParam String jobRole){
		return jobPipelineService.getapplicationsByTitle(jobRole);
	}


}
