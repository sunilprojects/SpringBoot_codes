package com.zoho.ats.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zoho.ats.entity.Candidate;
import com.zoho.ats.entity.Job;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
   

	boolean existsByPhoneNumber(String phone);

	Optional<Candidate> findByEmail(String email);
	



}

