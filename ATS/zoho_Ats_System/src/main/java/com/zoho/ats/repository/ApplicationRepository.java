package com.zoho.ats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoho.ats.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
          List<Application>  findByCandidateId(Long CandidateId);
         List<Application>  findByJobId(Long JobId);
}
