package com.zoho.ats.controller;

import com.zoho.ats.entity.Job;
import com.zoho.ats.entity.Recruiter;
import com.zoho.ats.repository.JobRepository;
import com.zoho.ats.repository.RecruiterRepository;
import com.zoho.ats.service.JobService;
import com.zoho.ats.service.RecruiterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private RecruiterRepository recruiterRepository;
    @Autowired
   private JobService jobService;

    @PostMapping("/recruiter/{recruiterId}")  // creating job for recruiter
    public ResponseEntity<Job> createJobForRecruiter(@PathVariable Long recruiterId, @RequestBody Job job) {
        return jobService.createJobRecruiter(recruiterId, job);
    }

    @PostMapping      // creating job for recruiter using request body
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
    	return jobService.creatingJob(job);
    }
    
    @GetMapping
    public List<Job> getAllJobs() {
    	return jobService.gettingAllJobs();
    }

    @GetMapping("/{id}")
    public Optional<Job> getJobById(@PathVariable Long id) {
    	return jobService.getJobByIds(id);
    }

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody Job jobDetails) {
    	return jobService.updatingJob(id, jobDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
    	 jobService.deletingId(id);
    }
    
    @GetMapping("/count")
    public ResponseEntity<String> getCount(){
    	long count=jobRepository.count();
    	return ResponseEntity.ok("Total jobPosts :"+count);
    }
}
