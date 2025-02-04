package com.broadcom.tanzu.hub.app.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.broadcom.tanzu.hub.app.demo.config.ProvisioningProperties;
import com.broadcom.tanzu.hub.app.demo.entity.ProvisionRequestEntity;
import com.broadcom.tanzu.hub.app.demo.model.Deployment;
import com.broadcom.tanzu.hub.app.demo.repository.ProvisionRequestRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Profile("provision")
@Slf4j
public class ProvisioningService {

    @Autowired
    private ProvisionRequestRepository provisionRequestRepository;

    @Autowired
    private ProvisioningProperties provisioningProperties;

    @Autowired
    private RestTemplate restTemplate;

    public UUID provision(Deployment deployment) {
        ProvisionRequestEntity requestEntity = ProvisionRequestEntity.builder()
                .name(deployment.getName())
                .description(deployment.getDescription())
                .deploymentId(deployment.getId())
                .state("new")
                .catalogId(deployment.getCatalogId())
                .inputs(deployment.getInputs())
                .build();

        requestEntity = provisionRequestRepository.save(requestEntity);

        return requestEntity.getId();
    }

    @Scheduled(fixedDelay = 30 * 60 * 1000)
    public void cleanUp() {
        log.info("provision cleanup task started");
        provisionRequestRepository.deleteAll();
        log.info("provision cleanup task completed");
    }

    public void serviceCallError() {

        String url = String.format("%s/api/deployment/%s", provisioningProperties.getDeploymentUrl(),
                UUID.randomUUID());

        ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

    }
}
