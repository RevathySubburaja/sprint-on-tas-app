package com.broadcom.tanzu.hub.app.demo.service;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.broadcom.tanzu.hub.app.demo.config.DeploymentProperties;
import com.broadcom.tanzu.hub.app.demo.entity.DeploymentEntity;
import com.broadcom.tanzu.hub.app.demo.model.ApprovalResponse;
import com.broadcom.tanzu.hub.app.demo.model.CatalogRequest;
import com.broadcom.tanzu.hub.app.demo.model.Deployment;
import com.broadcom.tanzu.hub.app.demo.repository.DeploymentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Profile("deployment")
@Slf4j
public class DeploymentService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DeploymentRepository deploymentRepository;

    @Autowired
    private DeploymentProperties deploymentProperties;

    @Scheduled(fixedDelay = 2 * 1000)
    @Transactional(
            readOnly = false,
            propagation = Propagation.REQUIRED
    )
    public void runNewDeployment() {
        DeploymentEntity deploymentEntity = deploymentRepository.findNewDeployment();
        if (deploymentEntity != null) {
            Deployment deployment = Deployment.builder()
                    .id(deploymentEntity.getId())
                    .inputs(deploymentEntity.getInputs())
                    .catalogId(deploymentEntity.getCatalogId())
                    .name(deploymentEntity.getName())
                    .description(deploymentEntity.getDescription())
                    .build();
            UUID approvalRequestId = createApprovalRequest(deployment);
            deploymentEntity.setState("waiting for approval");
            deploymentEntity.setApprovalRequestId(approvalRequestId);
            deploymentRepository.save(deploymentEntity);
        }
    }

    @Scheduled(fixedDelay = 2 * 1000)
    @Transactional(
            readOnly = false,
            propagation = Propagation.REQUIRED
    )
    public void runApprovedDeployment() {

        DeploymentEntity deploymentEntity = deploymentRepository.findApprovedDeployment();

        if (deploymentEntity != null) {
            Deployment deployment = Deployment.builder()
                    .id(deploymentEntity.getId())
                    .inputs(deploymentEntity.getInputs())
                    .catalogId(deploymentEntity.getCatalogId())
                    .name(deploymentEntity.getName())
                    .description(deploymentEntity.getDescription())
                    .build();
            String url = String.format("%s/api/provision", deploymentProperties.getProvisioningUrl());

            ResponseEntity<UUID> response = restTemplate.postForEntity(url, deployment, UUID.class);

            deploymentEntity.setProvisionRequestId(response.getBody());
            deploymentEntity.setState("provisioning");

            deploymentRepository.save(deploymentEntity);
        }
    }

    @Scheduled(fixedDelay = 30 * 60 * 1000)
    public void cleanUp() {
        log.info("deployment cleanup task started");
        deploymentRepository.deleteByState("provisioning");
        log.info("deployment cleanup task completed");
    }

    public UUID createApprovalRequest(Deployment deployment) {

        String url = String.format("%s/api/approval/check", deploymentProperties.getApprovalUrl());

        ResponseEntity<UUID> response = restTemplate.postForEntity(url, deployment, UUID.class);

        return response.getBody();
    }


    public void updateApprovalResponse(UUID deploymentId, ApprovalResponse approvalResponse) {
        DeploymentEntity deploymentEntity = deploymentRepository.findById(deploymentId).orElseThrow();

        deploymentEntity.setState(approvalResponse.getState());

        deploymentRepository.save(deploymentEntity);
    }

    public void serviceCallError() {

        String url = String.format("%s/api/approval/%s", deploymentProperties.getApprovalUrl(), UUID.randomUUID());

        ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

    }


    public UUID createDeployment(CatalogRequest catalogRequest) {

        DeploymentEntity deploymentEntity = DeploymentEntity.builder()
                .name(catalogRequest.getName())
                .catalogId(catalogRequest.getId())
                .description(catalogRequest.getDescription())
                .inputs(catalogRequest.getInputs())
                .state("new")
                .build();

        deploymentEntity = deploymentRepository.save(deploymentEntity);

        return deploymentEntity.getId();

    }

    public DeploymentEntity getDeployment(UUID deploymentId) {
        return deploymentRepository.findById(deploymentId).orElseThrow();
    }

    public List<DeploymentEntity> getDeployments() {
        return deploymentRepository.findAll();
    }
}
