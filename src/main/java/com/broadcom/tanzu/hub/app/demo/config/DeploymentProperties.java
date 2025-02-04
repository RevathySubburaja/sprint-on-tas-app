package com.broadcom.tanzu.hub.app.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Profile("deployment")
@ConfigurationProperties(prefix = "deployment")
public class DeploymentProperties {

    private String approvalUrl;

    private String provisioningUrl;
}
