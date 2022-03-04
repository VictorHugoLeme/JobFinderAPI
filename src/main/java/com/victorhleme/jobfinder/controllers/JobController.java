package com.victorhleme.jobfinder.controllers;

import com.victorhleme.jobfinder.dto.JobDTO;
import com.victorhleme.jobfinder.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public Page<JobDTO> findAll(Pageable page) {
        return jobService.findAll(page);
    }

    @GetMapping(value = "/{id}")
    public JobDTO findById(@PathVariable Integer id) {
        return jobService.findById(id);
    }

    @PostMapping()
    public JobDTO insert(@Valid @RequestBody JobDTO newJob) {
        return jobService.insert(newJob);
    }

    @PutMapping(value = "/{id}")
    public JobDTO update(@Valid @RequestBody JobDTO job, @PathVariable Integer id) {
        return jobService.update(job, id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable @Valid Integer id) {
        jobService.delete(id);
    }
}