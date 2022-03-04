package com.victorhleme.jobfinder.services;

import com.victorhleme.jobfinder.model.Job;
import com.victorhleme.jobfinder.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.stream.IntStream;

@Service
public class _DBService {

    @Autowired
    private JobRepository jobRepo;

    public void instantiateTestDatabase() throws ParseException, IOException {

        //instanciando os objetos de teste
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Job job = new Job();
            job.setTitle("Title" + i);
            job.setDescription("Description" + i);
            job.setMinSalary(10F*i);
            job.setMaxSalary(20F*i);
            jobRepo.save(job);
        });


    }

}
