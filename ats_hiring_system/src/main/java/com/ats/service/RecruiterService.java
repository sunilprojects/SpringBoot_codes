package com.ats.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ats.entity.Job;
import com.ats.entity.Recruiter;
import com.ats.exceptionHandling.RecruiterException;
import com.ats.repository.RecruiterRepository;
@Service
public class RecruiterService {
	@Autowired
	private RecruiterRepository recruiterRepository;

	public Recruiter saveRecruiter(Recruiter recruiter) {
		 boolean  present= recruiterRepository.existsByOfficialEmailAndMobileNumber(recruiter.getOfficialEmail(),recruiter.getMobileNumber());
		 if(present) {
			 throw new RecruiterException("Recruiter cannot be created change your credentials");
		 }
			 return recruiterRepository.save(recruiter);
	}

	public List<Recruiter> gettingAllRecruiters() {
		return recruiterRepository.findAll();
	}

	public Recruiter getRecruiter(Long id) {
	return recruiterRepository.findById(id)
			.orElseThrow(() -> new RuntimeException(" Id is not found: " + id));
		
	}
	
	
}
