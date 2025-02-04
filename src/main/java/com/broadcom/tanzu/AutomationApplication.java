package com.broadcom.tanzu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.broadcom.tanzu.hub.app.demo.config.ApplicationProperties;
import com.broadcom.tanzu.hub.app.demo.config.ApprovalProperties;
import com.broadcom.tanzu.hub.app.demo.config.CatalogProperties;
import com.broadcom.tanzu.hub.app.demo.config.Config;
import com.broadcom.tanzu.hub.app.demo.config.DeploymentProperties;
import com.broadcom.tanzu.hub.app.demo.config.ProvisioningProperties;


@SpringBootApplication
@EnableWebMvc
@Import(Config.class)
@EnableScheduling
@EnableConfigurationProperties({
        ApplicationProperties.class,
        ApprovalProperties.class,
        CatalogProperties.class,
        DeploymentProperties.class,
        ProvisioningProperties.class
})
public class AutomationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomationApplication.class, args);
    }
}
