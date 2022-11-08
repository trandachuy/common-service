/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the TermMetadata entity.
 */
public class TermMetadataDTO extends AbstractAuditingDTO implements Serializable {

    private static final long serialVersionUID = -3846716954854515272L;

    private Long id;

    private String metaKey;

    private String metaValue;


    private Long termId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }
    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TermMetadataDTO termMetadataDTO = (TermMetadataDTO) o;

        if ( ! Objects.equals(id, termMetadataDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TermMetadataDTO{" +
            "id=" + id +
            ", metaKey='" + metaKey + "'" +
            ", metaValue='" + metaValue + "'" +
            '}';
    }
}
