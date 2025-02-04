package com.broadcom.tanzu.hub.app.demo.repository;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.broadcom.tanzu.hub.app.demo.entity.DeploymentEntity;

@Repository
@Profile("deployment")
public interface DeploymentRepository extends JpaRepository<DeploymentEntity, UUID> {

    @Query(value = "SELECT * FROM deployment d where d.state = 'new' LIMIT 1 FOR UPDATE SKIP LOCKED", nativeQuery = true)
    DeploymentEntity findNewDeployment();

    @Query(value = "SELECT * FROM deployment d where d.state = 'approved' LIMIT 1 FOR UPDATE SKIP LOCKED", nativeQuery = true)
    DeploymentEntity findApprovedDeployment();

    int deleteByState(@Param("state") String state);
}
