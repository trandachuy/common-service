/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A DTO for the Term entity.
 * 
 * @deprecated since sprint 35. Move to catalog-shared
 */
@Deprecated
public class TermDTO extends AbstractAuditingDTO implements Serializable {

    private static final long serialVersionUID = -3846716954854515272L;

    private Long id;

    private String name;

    private String displayName;

    private Integer termLevel;

    private Integer termOrder;

    private List<TermDTO> children = new ArrayList<>();

    private Map<String, Object> metadata = new HashMap<>();

    private Long taxonomyId;

    private String taxonomy;

    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public Integer getTermLevel() {
        return termLevel;
    }

    public void setTermLevel(Integer termLevel) {
        this.termLevel = termLevel;
    }
    public Integer getTermOrder() {
        return termOrder;
    }

    public void setTermOrder(Integer termOrder) {
        this.termOrder = termOrder;
    }

    public List<TermDTO> getChildren() {
        return children;
    }

    public void setChildren(List<TermDTO> children) {
        this.children = children;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public Long getTaxonomyId() {
        return taxonomyId;
    }

    public void setTaxonomyId(Long taxonomyId) {
        this.taxonomyId = taxonomyId;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long termId) {
        this.parentId = termId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TermDTO termDTO = (TermDTO) o;

        if ( ! Objects.equals(id, termDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TermDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", displayName='" + displayName + "'" +
            ", termLevel='" + termLevel + "'" +
            ", termOrder='" + termOrder + "'" +
            '}';
    }
}
