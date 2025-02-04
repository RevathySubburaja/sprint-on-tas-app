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

import com.broadcom.tanzu.hub.app.demo.entity.ApprovalRequestEntity;
import com.broadcom.tanzu.hub.app.demo.model.Deployment;
import com.broadcom.tanzu.hub.app.demo.service.ApprovalService;

@RestController
@RequestMapping("/api/approval")
@Profile("approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping(path = "/check")
    public UUID checkApproval(@RequestBody Deployment deployment) {
        return approvalService.createApprovalRequest(deployment);
    }

    @GetMapping(path = "/{id}")
    public ApprovalRequestEntity getApprovalRequestById(@PathVariable(name = "id") UUID approvalRequestId) {
        return approvalService.getApprovalRequestById(approvalRequestId);
    }

    @GetMapping
    public List<ApprovalRequestEntity> getApprovalRequests() {
        return approvalService.getApprovalRequests();
    }

    @GetMapping(path = "/serviceCalls")
    public void serviceCallError() {
        approvalService.serviceCallError();
    }

}
