package com.planifcarbon.backend;

import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    @ExcludeFromJacocoGeneratedReport
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
