package com.zoho.ats.controller;


import com.zoho.ats.entity.Recruiter;
import com.zoho.ats.repository.RecruiterRepository;
import com.zoho.ats.service.RecruiterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {

    @Autowired
    private RecruiterRepository recruiterRepository;
    @Autowired
    private RecruiterService recruiterService;

    @PostMapping
    public Recruiter createRecruiter(@RequestBody Recruiter recruiter) {
       return recruiterService.saveRecruiter(recruiter);
    }

    @GetMapping
    public List<Recruiter> getAllRecruiters() {
    	return recruiterService.gettingAllRecruiters();
    }

    @GetMapping("/{id}")
    public Recruiter getRecruiterById(@PathVariable Long id) {
    	return recruiterService.getRecruiter(id);
    }
    
    @GetMapping("/count")
    public ResponseEntity<String> getCount(){
    	long count=recruiterRepository.count();
    	return ResponseEntity.ok("Recruiters Registerd :"+count);
    }
}
