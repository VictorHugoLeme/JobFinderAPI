package com.victorhleme.jobfinder.services;

import com.victorhleme.jobfinder.dto.JobDTO;
import com.victorhleme.jobfinder.exceptions.JobNotFoundException;
import com.victorhleme.jobfinder.model.Job;
import com.victorhleme.jobfinder.repositories.JobRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository repository;
    private final ModelMapper modelMapper;

    public Page<JobDTO> findAll(Pageable page) {
        Page<Job> entities = repository.findAll(page);
        return entities.map(this::convertToDTO);
    }

    public JobDTO findById(Integer id) {
        Job job = repository.findById(id).orElseThrow(() -> new JobNotFoundException(
                "Job not found! Id: "
                        + id + ", type: "
                        + Job.class));
        return convertToDTO(job);
    }

    public JobDTO insert(JobDTO jobDto) {
        jobDto.setId(repository.save(convertToModel(jobDto)).getId());

        return jobDto;
    }

    public JobDTO update(JobDTO jobDto, Integer id) {
        Job job = convertToModel(jobDto);
        job.setId(id);
        Integer newId = repository.save(job).getId();
        jobDto.setId(newId);
        return jobDto;
    }

    public void delete(Integer id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new JobNotFoundException(
                    "Job not found! Id: "
                            + id + ", type: "
                            + Job.class);
        }
    }

    public JobDTO convertToDTO(Job job) {
        return modelMapper.map(job, JobDTO.class);
    }

    public Job convertToModel(JobDTO jobDTO) {
        return modelMapper.map(jobDTO, Job.class);
    }

}
