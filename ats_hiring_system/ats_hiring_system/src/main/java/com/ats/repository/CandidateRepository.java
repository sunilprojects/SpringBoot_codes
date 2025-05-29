package com.ats.repository;


import com.ats.entity.Candidate;
import com.ats.entity.Job;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
   
   //get candidate by job id
	List<Candidate> findByJobId(Long jobId);
	
	// checks email and job in repository
	boolean existsByEmailAndJob(String email,Job job);
	boolean existsByPhoneNumber(String phone);
	



}

