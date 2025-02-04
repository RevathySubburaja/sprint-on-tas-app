package com.broadcom.tanzu.hub.app.demo.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.broadcom.tanzu.hub.app.demo.model.Deployment;
import com.broadcom.tanzu.hub.app.demo.service.ProvisioningService;

@RestController
@RequestMapping("/api/provision")
@Profile("provision")
public class ProvisioningController {

    @Autowired
    private ProvisioningService provisioningService;


    @PostMapping
    public UUID createDeployment(@RequestBody Deployment deployment) {
        return provisioningService.provision(deployment);
    }

    @GetMapping(path = "/serviceCalls")
    public void serviceCallError() {
        provisioningService.serviceCallError();
    }
}
