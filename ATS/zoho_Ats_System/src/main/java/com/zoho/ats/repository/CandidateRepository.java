package com.zoho.ats.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zoho.ats.entity.Candidate;
import com.zoho.ats.entity.Job;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
   
   //get candidate by job id
	List<Candidate> findByJobId(Long jobId);
	
	// checks email and job in repository
	boolean existsByEmailAndJob(String email,Job job);
	boolean existsByPhoneNumber(String phone);
	



}

