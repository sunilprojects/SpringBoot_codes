package com.ats.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL) //used to exclude fields with null values from the JSON response
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
  
    private String jobRole;
    private String company;
    private double Experience;
    private String location;
    private String skills;
    private String jobType;  // Full-time, Part-time, Internship, etc.
    private String description;
    private String Education;
   
    @OneToMany(mappedBy = "job")// one job can apply by many candidates
    private List<Candidate> candidates;
 
    @ManyToOne(fetch = FetchType.EAGER)// fetchType EAGER is default for Mto1 if u want to fetch 1 job u will get all jobs without using this
    @JoinColumn(name = "recruiter_id") // joining foreign key column in job
    @JsonIgnoreProperties("jobs")  // it will ignore  jobs property
    private Recruiter recruiter;
    
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // Prevents infinite recursion in JSON
    private List<Application> applications;


  
}
