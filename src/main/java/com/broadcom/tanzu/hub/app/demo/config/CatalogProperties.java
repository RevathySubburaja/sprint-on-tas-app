package com.broadcom.tanzu.hub.app.demo.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Profile("catalog")
@ConfigurationProperties(prefix = "catalog")
public class CatalogProperties {

    private String deploymentUrl;

}
