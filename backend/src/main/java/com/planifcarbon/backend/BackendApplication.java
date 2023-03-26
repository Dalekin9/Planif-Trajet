package com.planifcarbon.backend;

import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class of the application.
 */
@SpringBootApplication
public class BackendApplication {

    /**
     * Launch the spring boot application.
     * @param args a list of provided parameters.
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
