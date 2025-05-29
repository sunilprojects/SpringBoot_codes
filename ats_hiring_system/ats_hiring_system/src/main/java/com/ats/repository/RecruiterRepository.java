package com.ats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ats.entity.Recruiter;
@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

	boolean existsByOfficialEmailAndMobileNumber(String officialEmail, String mobileNumber);

}
