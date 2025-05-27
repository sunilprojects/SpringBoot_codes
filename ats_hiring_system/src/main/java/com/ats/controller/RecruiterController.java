package com.ats.controller;


import com.ats.entity.Recruiter;
import com.ats.repository.RecruiterRepository;
import com.ats.service.RecruiterService;

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
//     return ResponseEntity.ok("recruiter is created :"+ " " +recruiter.getId());
    }


    @GetMapping
    public List<Recruiter> getAllRecruiters() {
//        return recruiterRepository.findAll();
    	return recruiterService.gettingAllRecruiters();
    }

    @GetMapping("/{id}")
    public Recruiter getRecruiterById(@PathVariable Long id) {
//        return recruiterRepository.findById(id).orElse(null);
    	return recruiterService.getRecruiter(id);
    }
    @GetMapping("/count")
    public ResponseEntity<String> getCount(){
    	long count=recruiterRepository.count();
    	return ResponseEntity.ok("Recruiters Registerd :"+count);
    }
}
