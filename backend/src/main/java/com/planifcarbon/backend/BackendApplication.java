package com.planifcarbon.backend;

import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The main class of the application.
 */
@ComponentScan(basePackages = {"com.planifcarbon.backend.*"})
@SpringBootApplication
public class BackendApplication {

    /**
     * Launch the spring boot application.
     *
     * @param args a list of provided parameters.
     */
    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
         SpringApplication.run(BackendApplication.class, args);
    }

}
