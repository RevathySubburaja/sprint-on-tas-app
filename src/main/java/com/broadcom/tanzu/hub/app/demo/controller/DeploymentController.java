package com.broadcom.tanzu.hub.app.demo.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.broadcom.tanzu.hub.app.demo.entity.DeploymentEntity;
import com.broadcom.tanzu.hub.app.demo.model.ApprovalResponse;
import com.broadcom.tanzu.hub.app.demo.model.CatalogRequest;
import com.broadcom.tanzu.hub.app.demo.service.DeploymentService;

@RestController
@RequestMapping("/api/deployment")
@Profile("deployment")
public class DeploymentController {

    @Autowired
    private DeploymentService deploymentService;

    @GetMapping
    public List<DeploymentEntity> getDeployments() {
        return deploymentService.getDeployments();
    }

    @GetMapping(path = "/{id}")
    public DeploymentEntity getDeploymentById(@PathVariable(name = "id") UUID deploymentId) {
        return deploymentService.getDeployment(deploymentId);
    }

    @PostMapping
    public UUID createDeployment(@RequestBody CatalogRequest catalogRequest) {
        return deploymentService.createDeployment(catalogRequest);
    }

    @PostMapping(path = "/{id}/approvalResponse")
    public void approvalResponse(@PathVariable(name = "id") UUID deploymentId,
                                 @RequestBody ApprovalResponse approvalResponse) {
        deploymentService.updateApprovalResponse(deploymentId, approvalResponse);
    }

    @GetMapping(path = "/serviceCalls")
    public void serviceCallError() {
        deploymentService.serviceCallError();
    }

}
