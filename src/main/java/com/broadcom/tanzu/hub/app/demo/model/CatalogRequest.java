package com.broadcom.tanzu.hub.app.demo.model;

import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CatalogRequest {

    private UUID id;

    private String name;

    private String description;

    private Map<String, String> inputs;

    private UUID catalogId;
}
