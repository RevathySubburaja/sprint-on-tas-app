package com.broadcom.tanzu.hub.app.demo.repository;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.broadcom.tanzu.hub.app.demo.entity.CatalogEntity;

@Repository
@Profile("catalog")
public interface CatalogRepository extends JpaRepository<CatalogEntity, UUID> {
}
