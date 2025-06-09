package com.zoho.ats.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.zoho.ats.dto.JobPipelineDTO;
import com.zoho.ats.entity.Application;
import com.zoho.ats.entity.Candidate;
import com.zoho.ats.entity.Job;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
          List<Application>  findByCandidateId(Long CandidateId);
         List<Application>  findByJobId(Long JobId);
         
         //native query for recruiter dash board
         @Query(value = """
        		    SELECT 
        		        job_role AS jobTitle,
        		              COUNT(a.id) AS totalApplications,
	        SUM(CASE WHEN a.status = 'SHORTLISTED' THEN 1 ELSE 0 END) AS matchedCandidates,
	        SUM(CASE WHEN a.status = 'INTERVIEW' THEN 1 ELSE 0 END) AS interviews,
	        SUM(CASE WHEN a.status = 'OFFERED' THEN 1 ELSE 0 END) AS offers,
	        SUM(CASE WHEN a.status = 'HIRED' THEN 1 ELSE 0 END) AS hired,
	        SUM(CASE WHEN a.status = 'REJECTED' THEN 1 ELSE 0 END) AS rejected
	       From application a
     		    GROUP BY job_role
        		    """, nativeQuery = true) 
          List<JobPipelineDTO> getJobPipelinestatus();
         
      // Check if this candidate has already applied for the same job
		boolean existsByCandidateAndJob(Candidate candidate, Job job);
         

         



}
