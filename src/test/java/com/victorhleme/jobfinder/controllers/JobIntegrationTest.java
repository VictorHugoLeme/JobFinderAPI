package com.victorhleme.jobfinder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorhleme.jobfinder.dto.JobDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
class JobIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;



    @Nested
    @DisplayName("Tests for method findById")
    class FindByIdTests {

        @Test
        void findByIdTestSuccess() throws Exception {
            MvcResult result = mockMvc.perform(get("/jobs/{id}", 1)
                            .contentType("application/json"))
                    .andExpect(status().isOk()).andReturn();
            String content = result.getResponse().getContentAsString();
            Assertions.assertNotNull(content);


        }

        @Test
        void findByIdTestFail() throws Exception {
            MvcResult result = mockMvc.perform(get("/jobs/{id}", 32)
                            .contentType("application/json"))
                    .andExpect(status().isNotFound()).andReturn();
            int status = result.getResponse().getStatus();
            Assertions.assertEquals(404, status);
        }


    }

    @Nested
    @DisplayName("Tests for method insert")
    class InsertTests {


        @Test
        void insertTestSuccess() throws Exception {
            JobDTO insertedJob = new JobDTO(null, "Test insert title", "Test insert desc", 10.0, 20.0);
            MvcResult result = mockMvc.perform(post("/jobs")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(insertedJob)))
                    .andExpect(status().isOk()).andReturn();
            String content = result.getResponse().getContentAsString();
            String expectedResponse = "{\"id\":31,\"title\":\"Test insert title\",\"description\":\"Test insert desc\",\"minSalary\":10.0,\"maxSalary\":20.0}";

            Assertions.assertNotNull(content);
            Assertions.assertEquals(expectedResponse, content);
        }

        @Test
        void insertTestFail() throws Exception {

            JobDTO invalidJobTitle = new JobDTO(null, "", "Test", 10.0, 20.0);
            JobDTO invalidJobDescription = new JobDTO(null, "Test", "", 10.0, 20.0);
            MvcResult result = mockMvc.perform(post("/jobs")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(invalidJobTitle)))
                    .andExpect(status().is4xxClientError()).andReturn();
            int status = result.getResponse().getStatus();
            Assertions.assertEquals(400, status);

            result = mockMvc.perform(post("/jobs")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(invalidJobDescription)))
                    .andExpect(status().is4xxClientError()).andReturn();
            status = result.getResponse().getStatus();
            Assertions.assertEquals(400, status);
        }
    }



    @Test
    @DisplayName("Test for method update")
    void updateTest() throws Exception {
        JobDTO updatedJob = new JobDTO(2, "Test update title", "Test update desc", 20.0, 30.0);
        MvcResult result = mockMvc.perform(put("/jobs/2")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedJob)))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        String expectedResponse = "{\"id\":2,\"title\":\"Test update title\",\"description\":\"Test update desc\",\"minSalary\":20.0,\"maxSalary\":30.0}";
        Assertions.assertNotNull(content);
        Assertions.assertEquals(expectedResponse, content);
    }

    @Test
    @DisplayName("Test for method findAll")
    void findAllTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/jobs")
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }


    @Nested
    @DisplayName("Tests for method delete")
    class DeleteTests {

        @Test
        void deleteTestSuccess() throws Exception {
            MvcResult result = mockMvc.perform(delete("/jobs/{id}", 3)
                            .contentType("application/json"))
                    .andExpect(status().isOk()).andReturn();
            int successStatus = result.getResponse().getStatus();
            Assertions.assertEquals(200, successStatus);
        }
        @Test
        void deleteTestFail() throws Exception {
            MvcResult result = mockMvc.perform(delete("/jobs/{id}", 32)
                            .contentType("application/json"))
                    .andExpect(status().isNotFound()).andReturn();
            int failedStatus = result.getResponse().getStatus();
            Assertions.assertEquals(404, failedStatus);
    }

    }
}
