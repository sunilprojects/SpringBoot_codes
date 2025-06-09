package com.zoho.ats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zoho.ats.dto.RecruiterSummaryDTO;
import com.zoho.ats.entity.Recruiter;
@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

	boolean existsByOfficialEmailAndMobileNumber(String officialEmail, String mobileNumber);

    // native query to get all recruiters and totaljob posts
	@Query(value="""
			SELECT r.recruiter_name,r.official_email,r.company_name,count(*) AS TOTALJOBSPOSTED
			FROM recruiter r  JOIN job j ON r.id=j.recruiter_id
			GROUP BY r.id, r.recruiter_name, r.official_email, r.company_name
			""", nativeQuery=true)
		List<RecruiterSummaryDTO> getAllRecruitersSummary();

	
}
