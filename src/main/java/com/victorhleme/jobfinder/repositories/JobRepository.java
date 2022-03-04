package com.victorhleme.jobfinder.repositories;

import com.victorhleme.jobfinder.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {}