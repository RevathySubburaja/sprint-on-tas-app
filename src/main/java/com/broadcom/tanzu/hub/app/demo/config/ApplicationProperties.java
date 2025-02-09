package com.broadcom.tanzu.hub.app.demo.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private String externalUrl;
}
