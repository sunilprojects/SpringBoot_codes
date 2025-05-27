package com.ats.controller;


import com.ats.dto.CandidateDTO;
import com.ats.entity.Candidate;
import com.ats.entity.Job;
import com.ats.repository.CandidateRepository;
import com.ats.repository.JobRepository;
import com.ats.service.CandidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


//    @PostMapping("/job/{jobId}")
//    public Candidate createCandidate(@PathVariable Long jobId, @RequestBody Candidate candidate) {
//      
////        return candidateRepository.save(candidate);
//        return candidateService.candidateCreate(jobId,candidate);
//    }

    
    @PostMapping("/save/job/")
    public ResponseEntity<String>CandidateWithResume(
            @RequestParam("JobId/JobName") Long jobId,
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
    ) throws IOException {

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

        candidate.setResume(resumeFile.getBytes());

        // Optional: Store in filesystem instead
        /*
        String uploadDir = "uploads/resumes/";
        String fileName = UUID.randomUUID() + "_" + resumeFile.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(resumeFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        candidate.setResumePath(filePath.toString());
        */

        Candidate saved = candidateService.candidateCreate(jobId, candidate);
        return ResponseEntity.ok("Application Sent succesfully");
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
    
    @GetMapping("/job/{jobId}")
    public List<Candidate> getCandidatesByJob(@PathVariable Long jobId) {
        return candidateService.findCandidate(jobId);
    }

}
