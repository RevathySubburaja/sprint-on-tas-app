package com.broadcom.tanzu.hub.app.demo.repository;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.broadcom.tanzu.hub.app.demo.entity.ApprovalRequestEntity;

@Profile("approval")
@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequestEntity, UUID> {

    @Query(value = "SELECT * FROM approval_request a where a.state = 'pending' LIMIT 1 FOR UPDATE SKIP LOCKED", nativeQuery = true)
    ApprovalRequestEntity findPendingRequest();

    int deleteByState(@Param("state") String state);
}
