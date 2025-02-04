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

import com.broadcom.tanzu.hub.app.demo.config.ApprovalProperties;
import com.broadcom.tanzu.hub.app.demo.entity.ApprovalRequestEntity;
import com.broadcom.tanzu.hub.app.demo.model.ApprovalResponse;
import com.broadcom.tanzu.hub.app.demo.model.Deployment;
import com.broadcom.tanzu.hub.app.demo.repository.ApprovalRequestRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Profile("approval")
@Slf4j
public class ApprovalService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ApprovalProperties approvalProperties;

    @Autowired
    private ApprovalRequestRepository approvalRequestRepository;


    public ApprovalRequestEntity getApprovalRequestById(UUID approvalRequestId) {
        return approvalRequestRepository.findById(approvalRequestId).orElseThrow();
    }

    public List<ApprovalRequestEntity> getApprovalRequests() {
        return approvalRequestRepository.findAll();
    }


    public UUID createApprovalRequest(Deployment deployment) {
        ApprovalRequestEntity approvalRequestEntity = ApprovalRequestEntity.builder()
                .inputs(deployment.getInputs())
                .name(deployment.getName())
                .catalogId(deployment.getCatalogId())
                .deploymentId(deployment.getId())
                .description(deployment.getDescription())
                .state("pending")
                .build();
        approvalRequestEntity = approvalRequestRepository.save(approvalRequestEntity);

        return approvalRequestEntity.getId();
    }

    public void serviceCallError() {
        ApprovalResponse approvalResponse = ApprovalResponse.builder()
                .id(UUID.randomUUID())
                .state("approved")
                .build();

        String url = String.format("%s/api/deployment/%s/approvalResponse", approvalProperties.getDeploymentUrl(),
                UUID.randomUUID());

        ResponseEntity<Void> response = restTemplate.postForEntity(url, approvalResponse, Void.class);

    }

    @Scheduled(fixedDelay = 2 * 1000)
    @Transactional(
            readOnly = false,
            propagation = Propagation.REQUIRED
    )
    public void runApprovalRequests() {
        ApprovalRequestEntity approvalRequestEntity = approvalRequestRepository.findPendingRequest();
        if (approvalRequestEntity != null) {
            approvalRequestEntity.setState("approved");
            ApprovalResponse approvalResponse = ApprovalResponse.builder()
                    .id(approvalRequestEntity.getId())
                    .state("approved")
                    .build();

            String url = String.format("%s/api/deployment/%s/approvalResponse", approvalProperties.getDeploymentUrl(),
                    approvalRequestEntity.getDeploymentId());

            ResponseEntity<Void> response = restTemplate.postForEntity(url, approvalResponse, Void.class);
        }
    }

    @Scheduled(fixedDelay = 30 * 60 * 1000)
    public void cleanUp() {
        log.info("approval cleanup task started");
        approvalRequestRepository.deleteByState("approved");
        approvalRequestRepository.deleteByState("rejected");
        log.info("approval cleanup task completed");
    }
}
