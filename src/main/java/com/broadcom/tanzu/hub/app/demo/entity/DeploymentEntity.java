package com.broadcom.tanzu.hub.app.demo.entity;


import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.context.annotation.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "deployment")
@Table(name = "deployment")
@Builder
@Profile("deployment")
public class DeploymentEntity {

    @Id
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private UUID catalogId;

    @Column
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> inputs;

    private String state;

    @Column
    private UUID provisionRequestId;

    @Column
    private UUID approvalRequestId;
}
