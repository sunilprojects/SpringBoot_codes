package com.ats.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ats.entity.Job;
import com.ats.entity.Recruiter;
import com.ats.repository.JobRepository;
import com.ats.repository.RecruiterRepository;
@Service
public class JobService {
	@Autowired
	private JobRepository jobRepository;
	  @Autowired
	    private RecruiterRepository recruiterRepository;

	public ResponseEntity<Job> createJobRecruiter(Long recruiterId, Job job) {
		 Recruiter recruiter = recruiterRepository.findById(recruiterId).orElse(null);
	        if (recruiter == null) {
	            return ResponseEntity.notFound().build();
	        }
	        job.setRecruiter(recruiter);
	        Job savedJob = jobRepository.save(job);
	        return ResponseEntity.ok(savedJob);
	}

	public ResponseEntity<Job> creatingJob(Job job) {
		Job savedJob=jobRepository.save(job);
		return ResponseEntity.ok(savedJob);
	}

	public List<Job> gettingAllJobs() {
		return jobRepository.findAll();
	}

	public Optional<Job> getJobByIds(Long id) {
		return jobRepository.findById(id);
	}

	public Job updatingJob(Long id, Job jobDetails) {
		Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            job.setJobRole(jobDetails.getJobRole());
            job.setCompany(jobDetails.getCompany());
            job.setExperience(jobDetails.getExperience());
            job.setDescription(jobDetails.getDescription());
            job.setLocation(jobDetails.getLocation());
            job.setJobType(jobDetails.getJobType());
            job.setSkills(jobDetails.getSkills());
            job.setEducation(jobDetails.getEducation());
            return jobRepository.save(job);
        }
        return null;
	}

	public void deletingId(Long id) {
		 jobRepository.deleteById(id);
	}

}
