package com.broadcom.tanzu.appdataingest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@Import(ConfigOld.class)
public class AppDataIngestApplication {

    public static void mainTest(String[] args) {
        SpringApplication.run(AppDataIngestApplication.class, args);
    }

}
