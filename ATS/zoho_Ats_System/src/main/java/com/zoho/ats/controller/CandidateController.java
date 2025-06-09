package com.zoho.ats.controller;


import com.zoho.ats.dto.CandidateDTO;
import com.zoho.ats.dto.CandidateJobApplicationDTO;
import com.zoho.ats.entity.Application;
import com.zoho.ats.entity.Candidate;
import com.zoho.ats.entity.Job;
import com.zoho.ats.repository.CandidateRepository;
import com.zoho.ats.repository.JobRepository;
import com.zoho.ats.service.ApplicationService;
import com.zoho.ats.service.CandidateService;
import com.zoho.ats.service.ResumeScreenService;

import jakarta.persistence.criteria.Path;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private ResumeScreenService resumeScreenService;
    @Autowired
    private ApplicationService applicationService;
    
    @PostMapping("/save/job/")
    public ResponseEntity<String>CandidateWithResume(
            @RequestParam("JobId") Long jobId,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("location") String location,
            @RequestParam("DOB")  LocalDate dateOfBirth,
            @RequestParam("Education") String highestQualification,
            @RequestParam("skills") String skills,
            @RequestParam("Yop") long yop,
            @RequestParam("resume") MultipartFile resumeFile
    ) throws IOException, TikaException {

    	String resumePath = resumeScreenService.saveResumeToFileSystem(resumeFile);

        // 2. Extract resume text
        String resumeText = resumeScreenService.extractTextFromResume(new File(resumePath));

        // 3. Get required skills from job
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + jobId));
        String requiredSkills = job.getSkills();  // make sure job has `skills` field

        // 4. Extract matched skills from resume
        List<String> matchedSkills = resumeScreenService.extractMatchingSkills(resumeText, requiredSkills);
        System.out.println("Matched Skills: " + matchedSkills);
    	
        Candidate candidate = new Candidate();
        candidate.setFirstName(firstName);
        candidate.setLastName(lastName);
        candidate.setEmail(email);
        candidate.setPhoneNumber(phoneNumber);
        candidate.setLocation(location);
        candidate.setDateOfBirth(dateOfBirth);
        candidate.setQual(highestQualification);
        candidate.setSkills(skills);
        candidate.setYearOfPassing(yop);
        candidate.setResumePath(resumePath);

        Candidate saved = candidateService.candidateCreate(jobId, candidate);
        return ResponseEntity.ok("Application submitted successfully with matched skills: " + matchedSkills);
    }
    

    @GetMapping
     public List<CandidateDTO> getAllCandidates() {
    	
    	 return	candidateService.getAll();
     }
    
     @GetMapping("/id/{id}")
    public Optional<Candidate> getCandidatebyId(@PathVariable Long id){
    	return candidateService.getbyId(id);
    }
     
    @PutMapping("/{id}")
    public Candidate updateCandidate(@PathVariable long id, @RequestBody Candidate candidateDetails) {
    	return candidateService.candidateUpdate(id,candidateDetails);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity< String> deleteCandidate(@PathVariable long id) {
    	candidateService.deleteCandidateById(id);
    	return ResponseEntity.ok("given id " +id +"is deleted");
    }
    
//    @GetMapping("/job/{jobId}")
//    public List<Candidate> getCandidatesByJob(@PathVariable Long jobId) {
//        return candidateService.findCandidate(jobId);
//    }
    
    @GetMapping("/count") // total candidate present in candidate table
    public ResponseEntity<String> getCount(){
    	long count=candidateRepository.count();
    	return ResponseEntity.ok("Total candidates :"+count);
    }
    
    @GetMapping("/total-applications") // fetching total applications applied by candidate
    public List<Application> getApplications(@RequestParam Long candidateId){
    	    return applicationService.getApplicationsByCandidateId(candidateId);
    }
    
    @GetMapping("/candidate/status")//to get candidate application status by candidateId
    public List<CandidateJobApplicationDTO> getCandidateApplicationStatus(@RequestParam Long candidateId){
    	return candidateService.getCandidateApplicationStatus(candidateId);
    }

}
