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

import com.broadcom.tanzu.hub.app.demo.entity.CatalogEntity;
import com.broadcom.tanzu.hub.app.demo.entity.CatalogRequestEntity;
import com.broadcom.tanzu.hub.app.demo.model.CatalogUserRequest;
import com.broadcom.tanzu.hub.app.demo.service.CatalogService;

@RestController
@RequestMapping("/api/catalog")
@Profile("catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping
    public List<CatalogEntity> getCatalogs() {
        return catalogService.getCatalogs();
    }

    @GetMapping(path = "/{id}")
    public CatalogEntity getCatalogById(@PathVariable(name = "id") UUID catalogId) {
        return catalogService.getCatalogById(catalogId);
    }

    @PostMapping(path = "/{id}/request")
    public UUID requestCatalog(@PathVariable(name = "id") UUID catalogId,
                               @RequestBody CatalogUserRequest catalogUserRequest) {
        return catalogService.requestCatalog(catalogId, catalogUserRequest);
    }

    @GetMapping(path = "/{id}/request")
    public List<CatalogRequestEntity> getCatalogRequests(@PathVariable(name = "id") UUID catalogId) {
        return catalogService.getCatalogRequests();
    }

    @GetMapping(path = "/{id}/request/{catalogRequestId}")
    public CatalogRequestEntity getCatalogRequests(@PathVariable(name = "id") UUID catalogId,
                                                   @PathVariable(name = "id") UUID catalogRequestId) {
        return catalogService.getCatalogRequestById(catalogRequestId);
    }

    @GetMapping(path = "/serviceCalls")
    public void getApprovalRequestById() {
        catalogService.serviceCallError();
    }
}
