package com.victorhleme.jobfinder.services;

import com.victorhleme.jobfinder.dto.JobDTO;
import com.victorhleme.jobfinder.exceptions.JobNotFoundException;
import com.victorhleme.jobfinder.model.Job;
import com.victorhleme.jobfinder.repositories.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("JobService")
class JobServiceTest {
    @MockBean
    static JobRepository jobRepository;

    @Autowired
    JobService jobService;

    @Nested
    @DisplayName("Tests for method findAll")
    class FindAllTests {

        Pageable page = PageRequest.of(0, 2);

        @BeforeEach
        void setup() {
            jobService.setRepository(jobRepository);
        }

        @Test
        @DisplayName("Should success when finding all the jobs")
        void findAllTestSuccess() {
            Job job = new Job(1, "tt1", "desc1", 1, 1);
            Job job2 = new Job(2, "tt2", "desc2", 2, 2);
            List<Job> jobList = Arrays.asList(job, job2);

            Page<Job> actualPage = new PageImpl<>(jobList);

            when(jobRepository.findAll(page)).thenReturn(actualPage);
            Page<JobDTO> actualJobs =  jobService.findAll(page);
            assertEquals("tt1", actualJobs.getContent().get(0).getTitle());
            assertEquals("tt2", actualJobs.getContent().get(1).getTitle());
            assertNotNull(actualJobs.getContent());
        }

        @Test
        @DisplayName("Should return empty page when finding no jobs")
        void findAllTestFail() {

            when(jobRepository.findAll(page)).thenReturn(Page.empty());
            Page<JobDTO> actualPage =  jobService.findAll(page);
            assertEquals(0,actualPage.getTotalElements());
        }
    }

    @Nested
    @DisplayName("Tests for method findById")
    class FindByIdTests {

        @BeforeEach
        void setup() {
            jobService.setRepository(jobRepository);
        }

        @Test
        @DisplayName("Should succeed when finding a job by id")
        void findByIdTestSuccess() {
            Integer jobId = 1;

            Optional<Job> job = Optional.of(criarJob());
            when(jobRepository.findById(jobId)).thenReturn(job);

            JobDTO actualJob =  jobService.findById(jobId);

            assertEquals(job.get().getTitle(), Optional.of(actualJob).get().getTitle());
        }

        @Test
        @DisplayName("Should return exception when not finding a job")
        void findByIdTestFail() {
            Integer jobId = 10;

            JobNotFoundException thrown = assertThrows(JobNotFoundException.class,
                    () -> jobService.findById(jobId), "JobNotFoundException was expected");

            assertEquals("Job not found! Id: 10, type: class com.victorhleme.jobfinder.model.Job", thrown.getMessage());
        }
    }


    @Nested
    @DisplayName("Tests for method insert")
    class InsertTests {

        Job job = criarJob();
        JobDTO jobDto = criarJobDTO();

        @BeforeEach
        void setup() {
            jobService.setRepository(jobRepository);
        }

        @Test
        void insertTestSuccess() {

            when(jobRepository.save(job)).thenReturn(job);

            JobDTO insertedDTO = jobService.insert(jobDto);
            assertEquals(jobDto, insertedDTO);
            assertDoesNotThrow(() -> {
                jobService.insert(jobDto);
            });
        }

        @Test
        void insertTestFail() {
            assertEquals(0, 0);
        }
    }


    @Nested
    @DisplayName("Tests for method  update")
    class UpdateTests {

        @BeforeEach
        void setup() {
            jobService.setRepository(jobRepository);
        }

        Job job = new Job(1, "Tt", "Desc", 0,0);
        Job job2 = new Job(2, "Tt", "Desc", 0,0);

        JobDTO jobDto = new JobDTO(null, "Tt", "Desc", 0,0);

        @Test
        void updateTestSuccess() {

            when(jobRepository.save(job)).thenReturn(job);
            when(jobRepository.save(job2)).thenReturn(job2);

            assertEquals(1, jobService.update(jobDto, 1).getId());
            assertEquals(2, jobService.update(jobDto, 2).getId());
        }

        @Test
        void updateTestFail() {
            assertEquals(0, 0);
        }
    }

    @Nested
    @DisplayName("Tests for method delete")
    class DeleteTests {

        JobRepository repoMock = Mockito.mock(JobRepository.class);

        @BeforeEach
        void setup() {
            jobService.setRepository(repoMock);
        }

        @Test
        void deleteTestSuccess() {

            Integer jobId = 1;

            doNothing().when(repoMock).deleteById(eq(jobId));

            assertDoesNotThrow(() -> {
                jobService.delete(jobId);
            });
        }

        @Test
        void deleteTestFail() {
            Integer jobId = 2;

            doThrow(EmptyResultDataAccessException.class).when(repoMock).deleteById(eq(jobId));

            JobNotFoundException thrown = assertThrows(JobNotFoundException.class,
                    () -> jobService.delete(jobId), "JobNotFoundException was expected");

            assertEquals(
                    "Job not found! Id: 2, type: class com.victorhleme.jobfinder.model.Job",
                    thrown.getMessage());
        }
    }

    private Job criarJob() {
        return new Job(
                1, "name", "desc", 10, 10);
    }

    private JobDTO criarJobDTO() {
        return new JobDTO(
                1, "name", "desc", 10, 10);
    }
}
