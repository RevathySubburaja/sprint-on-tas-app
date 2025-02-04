package com.broadcom.tanzu.hub.app.demo.tasks;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.broadcom.tanzu.hub.app.demo.config.ApplicationProperties;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MetricsGenTask {


    private static final String SAMPLE_STRING = "In 1992, Tim Berners-Lee circulated a document titled HTML Tags which outlined just 20 tags, many of which are now obsolete or have taken other forms. The first surviving tag to be defined in the document, after the crucial anchor tag, is the paragraph tag. It wasn’t until 1993 that a discussion emerged on the proposed image tag.In 1992, Tim Berners-Lee circulated a document titled HTML Tags,   which outlined just 20 tags, many of which are now obsolete or have taken other forms. In 1992, Tim Berners-Leeaa";

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private RestTemplate restTemplate;


    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void runCpuIntensiveTask() {
        log.info("cpu intensive task started");
        for (int j = 0; j < 2000; j++) {
            try {
                FileWriter myWriter = new FileWriter("tmp.txt");
                for (int i = 0; i < 20000; i++) {
                    myWriter.write(SAMPLE_STRING);
                }
                myWriter.close();
                File f = new File("tmp.txt");
                f.delete();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        log.info("cpu intensive task completed");
    }

    @Scheduled(fixedDelay = 15 * 60 * 1000, initialDelay = 10 * 60 * 1000)
    public void runMemoryFillTask() {
        log.info("memory fill task started");
        List<MemoryFillObject> objects = new ArrayList<>();
        for (int j = 0; j < 1024 * 100; j++) {
            MemoryFillObject memoryFillObject = MemoryFillObject.builder()
                    .name1(SAMPLE_STRING)
                    .name2(SAMPLE_STRING)
                    .build();
            objects.add(memoryFillObject);
        }
        log.info("memory fill task completed");
    }


    @Scheduled(fixedDelay = 2 * 60 * 1000)
    public void runExternalCall() {
        log.info("external call task started");
        for (int j = 0; j < 3; j++) {
            try {
                ResponseEntity<Void> response = restTemplate.getForEntity(applicationProperties.getExternalUrl(),
                        Void.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("external task completed");
    }
}
