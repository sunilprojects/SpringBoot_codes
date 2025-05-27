package com.ats.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ats.dto.CandidateDTO;
import com.ats.entity.Application;
import com.ats.entity.Candidate;
import com.ats.entity.Job;
import com.ats.enums.ApplicationStatus;
import com.ats.exceptionHandling.EmailExistException;
import com.ats.repository.ApplicationRepository;
import com.ats.repository.CandidateRepository;
import com.ats.repository.JobRepository;
@Service
public class CandidateService {
	  @Autowired
	    private JobRepository jobRepository;
	    @Autowired
	    private CandidateRepository candidateRepository;
	  
	    @Autowired
	    private ApplicationRepository applicationRepository;
	    
	    
	    public Candidate candidateCreate(long jobId,Candidate candidate) {
	    	Job job = jobRepository.findById(jobId)
	    			.orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId)); // fetching job object from db if not throw error
	    	candidate.setJob(job);
	    	// it checks the database whether this email and jobid is present are not
	    	boolean alreadyApplied = candidateRepository.existsByEmailAndJob(candidate.getEmail(), job);
//	    	boolean phoneApplied=candidateRepository.existsByPhoneNumber(candidate.getPhoneNumber());
	    	if (alreadyApplied) {
	    		throw new EmailExistException("Email is already applied for this job."); // Custom Exception
	    	}
	    candidate.setStatus(ApplicationStatus.APPLIED);//trigers in candidate table once candidate apply
//	      application.setCandidate(candidate.getId());
	    Application application= new Application();
	       Candidate savedCandidate=candidateRepository.save(candidate);
	       
	       application.setCandidate(savedCandidate);
	       application.setJob(job);
	       application.setStatus(ApplicationStatus.APPLIED);
	      application.setAppliedDate(LocalDateTime.now());// or set your date format
	      applicationRepository.save(application);
	       return savedCandidate;
	    }
	
	public Optional<Candidate> getbyId(Long id) {
		return candidateRepository.findById(id);
	}

	public List<CandidateDTO> getAll() {
		List<Candidate> candidates = candidateRepository.findAll();

	    return candidates.stream()
	            .map(candidate -> new CandidateDTO(
	                    candidate.getId(),
	                    candidate.getFirstName(),
	                    candidate.getLastName(),
	                    candidate.getEmail(),
	                    candidate.getPhoneNumber(),
	                    candidate.getLocation(),
	                    candidate.getSkills()
	            ))
	            .collect(Collectors.toList());
	}

	public Candidate candidateUpdate(Long id, Candidate candidateDetails) {
	    Optional<Candidate> candidate = candidateRepository.findById(id);
	    if (candidate.isPresent()) {//checks for candidate object present or not
	        Candidate existingCandidate = candidate.get(); // get gives candidate object
	        existingCandidate.setFirstName(candidateDetails.getFirstName());
	        existingCandidate.setLastName(candidateDetails.getLastName());
	        existingCandidate.setEmail(candidateDetails.getEmail());
	        existingCandidate.setPhoneNumber(candidateDetails.getPhoneNumber());
	        existingCandidate.setResume(candidateDetails.getResume());
	        existingCandidate.setJob(candidateDetails.getJob());
	        return candidateRepository.save(existingCandidate);
	    } else {
	        throw new NoSuchElementException("Candidate with ID " + id + " not found.");
	    }
	
}
	public void deleteCandidateById(Long id) {
        candidateRepository.deleteById(id);
	}

	public List<Candidate> findCandidate(Long jobId) {
		return candidateRepository.findByJobId(jobId);
	}



}
