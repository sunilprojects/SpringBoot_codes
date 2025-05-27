package com.ats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ats.entity.Application;
public interface ApplicationRepository extends JpaRepository<Application, Long> {
          List<Application>  findByCandidateId(Long CandidateId);
         List<Application>  findByJobId(Long JobId);
}
