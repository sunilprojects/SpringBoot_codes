package com.zoho.ats.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoho.ats.dto.JobPipelineDTO;
import com.zoho.ats.entity.Application;
import com.zoho.ats.repository.ApplicationRepository;

@Service
public class JobPipelineService {
	@Autowired
	private ApplicationRepository applicationRepository;
	
	// to get complete application dashboard
	public List<JobPipelineDTO> getapplicationStatus(){
		List<JobPipelineDTO> dashboard=applicationRepository.getJobPipelinestatus();
		
		return dashboard.stream().map(ls->
		new JobPipelineDTO(
				ls.getJobTitle(),
				ls.getTotalApplications(),
				ls.getMatchedCandidates(),
				ls.getInterviews(),
				ls.getOffers(),
				ls.getHired(),
				ls.getRejected()
				)).collect(Collectors.toList());
		
	}
	// to get role based dashboard
	public List<JobPipelineDTO> getapplicationsByTitle(String jobRole){
		 List<JobPipelineDTO> allJobs = applicationRepository.getJobPipelinestatus();
	        return allJobs.stream()
	                      .filter(job -> job.getJobTitle().equalsIgnoreCase(jobRole))
	                      .collect(Collectors.toList());
	}

}
