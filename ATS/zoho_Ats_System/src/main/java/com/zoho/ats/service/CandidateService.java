package com.zoho.ats.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zoho.ats.dto.ApplicationDTO;
import com.zoho.ats.dto.CandidateDTO;
import com.zoho.ats.dto.CandidateJobApplicationDTO;
import com.zoho.ats.dto.CandidateMatchDTO;
import com.zoho.ats.entity.Application;
import com.zoho.ats.entity.Candidate;
import com.zoho.ats.entity.Job;
import com.zoho.ats.enums.ApplicationStatus;
import com.zoho.ats.exceptionHandling.EmailExistException;
import com.zoho.ats.repository.ApplicationRepository;
import com.zoho.ats.repository.CandidateRepository;
import com.zoho.ats.repository.JobRepository;


import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class CandidateService {
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private ApplicationRepository applicationRepository;
	
	public Candidate candidateCreate(long jobId, Candidate candidate) {
	    Job job = jobRepository.findById(jobId)
	            .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));

	    Candidate savedCandidate;// declaring a variable 

	    //  Check if candidate already exists
	    Optional<Candidate> existingCandidate = candidateRepository.findByEmail(candidate.getEmail());

	    if (existingCandidate.isPresent()) {
	        savedCandidate = existingCandidate.get();

	        // Check if this candidate has already applied for the same job
	        boolean alreadyApplied = applicationRepository.existsByCandidateAndJob(savedCandidate, job);
	        if (alreadyApplied) {
	            throw new EmailExistException("You have already applied for this job.");
	        }

	    } else {
	        savedCandidate = candidateRepository.save(candidate);
	        System.out.println("New Candidate Created: ID = " + savedCandidate.getId());
	    }

	    //  Save Application for this job
	    Application application = new Application();
	    application.setCandidate(savedCandidate);
	    application.setJob(job);
	    application.setStatus(ApplicationStatus.APPLIED);
	    application.setAppliedDate(LocalDateTime.now());
	    application.setJobRole(job.getJobRole());

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
			existingCandidate.setResumePath(candidateDetails.getResumePath());
//			existingCandidate.setJob(candidateDetails.getJob());
			return candidateRepository.save(existingCandidate);
		} else {
			throw new NoSuchElementException("Candidate with ID " + id + " not found.");
		}

	}
	public void deleteCandidateById(Long id) {
		candidateRepository.deleteById(id);
	}

//	public List<Candidate> findCandidate(Long jobId) {
//		return candidateRepository.findByJobId(jobId);
//	}

	// to save resumes in folder
	public String saveResumeToFileSystem(MultipartFile resumeFile) throws IOException {
	    String uploadDir = "resumes/";   // directory
	    String fileName = UUID.randomUUID() + "_" + resumeFile.getOriginalFilename(); // it will generate unique code, universally unique identifiers 
	    File directory = new File(uploadDir);
	    if (!directory.exists()) {
	        directory.mkdirs(); // Create directory if it doesn't exist
	    }

	    File resume = new File(uploadDir + fileName);  //creating object of file
	    System.out.println("resume is:"+resume);
	    
	    try (OutputStream os = new FileOutputStream(resume)) {
	        os.write(resumeFile.getBytes());//Converts the uploaded resume to bytes and writes them to a new file on disk.
	    }

	    return resume.getAbsolutePath();
	}
   
     // to get candidate application status by candidateId
	 public List<CandidateJobApplicationDTO> getCandidateApplicationStatus(Long candidateId) {
	List<Application> app=applicationRepository.findByCandidateId(candidateId);
	       return app.stream().map(ap->{
	       CandidateJobApplicationDTO dto= new CandidateJobApplicationDTO();
	        dto.setJobTitle(ap.getJobRole());
	        dto.setCompanyName(ap.getJob().getCompany());
	        dto.setAppliedDate(ap.getAppliedDate());
	        dto.setStatus(ap.getStatus());
	        return dto;
	       }).collect(Collectors.toList());
	
	 }


}
