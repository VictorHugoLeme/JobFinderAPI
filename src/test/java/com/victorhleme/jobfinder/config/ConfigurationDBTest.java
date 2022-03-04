package com.victorhleme.jobfinder.config;

import java.io.IOException;
import java.text.ParseException;

import com.victorhleme.jobfinder.services._DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("test")
public class ConfigurationDBTest {

    @Autowired
    private _DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException, IOException {
        dbService.instantiateTestDatabase();
        return true;
    }

}