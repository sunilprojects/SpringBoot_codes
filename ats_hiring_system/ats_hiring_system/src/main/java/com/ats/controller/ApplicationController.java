package com.ats.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ats.dto.ApplicationDTO;
import com.ats.dto.CandidateMatchDTO;
import com.ats.entity.Application;
import com.ats.enums.ApplicationStatus;
import com.ats.repository.ApplicationRepository;
import com.ats.service.ApplicationService;

@RestController
@RequestMapping("api/applications")
public class ApplicationController {
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ApplicationRepository applicationRepository;


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

	@GetMapping("/all-emails/{jobId}")
	public ResponseEntity<List<CandidateMatchDTO>>  getMatchingEmails(@PathVariable Long jobId){
		List<CandidateMatchDTO> candidateDetails= applicationService.getMatchingCandidateEmails(jobId);
		return ResponseEntity.ok(candidateDetails);
	}

}
