package com.planifcarbon.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = BackendApplication.class)
@TestPropertySource(
        locations = "classpath:application-tests.properties")
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }
}
