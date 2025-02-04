package com.broadcom.tanzu.appdataingest;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ObservationRegistry observationRegistry;

    @Value("SERVICE1:localhost")
    private String service1;

    @Value("SERVICE2:localhost")
    private String service2;

    @Value("SERVICE3:localhost")
    private String service3;

    @PostMapping("/api/app/data")
    public void handleAppData(@RequestBody Map<String, Object> data) {

        Observation ob = Observation.createNotStarted("some-operation", this.observationRegistry);
        ob.contextualName("printing hello world")
                .observe(() -> {
                    String fooResourceUrl = String.format("http://%s:8080/api/app2/data", service2);
                    ResponseEntity<Void> response = restTemplate.postForEntity(fooResourceUrl, data, Void.class);
                    System.out.println("hello world");
                });
    }

    @PostMapping("/api/app/data1")
    public void handleAppData4(@RequestBody Map<String, Object> data) {

        Observation ob = Observation.createNotStarted("some-operation", this.observationRegistry);
        ob.contextualName("printing hello world")
                .lowCardinalityKeyValue("api", "api/app/data")
                .observe(() -> {
                    String fooResourceUrl = String.format("http://%s:8080/api/app2/data", service3);
                    ResponseEntity<Void> response = restTemplate.postForEntity(fooResourceUrl, data, Void.class);
                    System.out.println("hello world");
                });
    }

    @PostMapping("/api/app3/data")
    public void handleAppData3(@RequestBody Map<String, Object> data) {

        Observation ob = Observation.createNotStarted("some-operation", this.observationRegistry);
        ob.contextualName("printing hello world")
                .lowCardinalityKeyValue("api", "api/app3/data")
                .observe(() -> {
                    System.out.println("hello world");
                    try {
                        ResponseEntity<Void> response = restTemplate.postForEntity("http://sampleurl.test.url.com",
                                data, Void.class);
                        System.out.println("hello world....");
                    } catch (Exception w) {
                        w.printStackTrace();
                    }
                });
    }


    @PostMapping("/api/app2/data")
    public void handleAppData2(@RequestBody Map<String, Object> data) {


        Observation ob = Observation.createNotStarted("some-operation", this.observationRegistry);
        ob.contextualName("printing hello world2")
                .lowCardinalityKeyValue("api", "api/app2/data")
                .observe(() -> {
                    String fooResourceUrl = String.format("http://%s:8080/api/app3/data", service3);
                    ResponseEntity<Void> response = restTemplate.postForEntity(fooResourceUrl, data, Void.class);
                    System.out.println("hello world");
                });
    }

}
