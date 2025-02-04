package com.broadcom.tanzu.hub.app.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.broadcom.tanzu.hub.app.demo.config.CatalogProperties;
import com.broadcom.tanzu.hub.app.demo.entity.CatalogEntity;
import com.broadcom.tanzu.hub.app.demo.entity.CatalogRequestEntity;
import com.broadcom.tanzu.hub.app.demo.model.CatalogUserRequest;
import com.broadcom.tanzu.hub.app.demo.repository.CatalogRepository;
import com.broadcom.tanzu.hub.app.demo.repository.CatalogRequestRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Profile("catalog")
@Slf4j
public class CatalogService {

    @Autowired
    private CatalogProperties catalogProperties;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private CatalogRequestRepository catalogRequestRepository;

    @Autowired
    private RestTemplate restTemplate;


    public List<CatalogEntity> getCatalogs() {
        return catalogRepository.findAll();
    }

    public CatalogEntity getCatalogById(UUID id) {
        return catalogRepository.findById(id).get();
    }

    public CatalogRequestEntity getCatalogRequestById(UUID id) {
        return catalogRequestRepository.findById(id).orElseThrow();
    }

    public List<CatalogRequestEntity> getCatalogRequests() {
        return catalogRequestRepository.findAll();
    }

    public void serviceCallError() {

        String url = String.format("%s/api/deployment/%s", catalogProperties.getDeploymentUrl(), UUID.randomUUID());

        ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

    }

    public UUID requestCatalog(UUID id, CatalogUserRequest catalogUserRequest) {
        CatalogEntity catalogEntity = catalogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("catalog not found"));

        CatalogRequestEntity requestEntity = CatalogRequestEntity.builder()
                .name(catalogUserRequest.getName())
                .description(catalogUserRequest.getDescription())
                .inputs(catalogUserRequest.getInputs())
                .catalogId(catalogEntity.getId())
                .build();


        requestEntity = catalogRequestRepository.save(requestEntity);

        String url = String.format("%s/api/deployment", catalogProperties.getDeploymentUrl());

        ResponseEntity<UUID> response = restTemplate.postForEntity(url, requestEntity, UUID.class);

        return response.getBody();

    }

    @Scheduled(fixedDelay = 30 * 60 * 1000)
    public void cleanUp() {
        log.info("catalog cleanup task started");
        catalogRequestRepository.deleteAll();
        log.info("catalog cleanup task completed");
    }
}
