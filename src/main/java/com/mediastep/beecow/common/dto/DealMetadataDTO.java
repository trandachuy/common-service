/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 7/3/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 */

package com.mediastep.beecow.common.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the DealMetadata entity.
 */
public class DealMetadataDTO implements Serializable {

    private Long id;

    private String metaKey;

    private String metaValue;

    private Long dealSettingId;

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

    public Long getDealSettingId() {
        return dealSettingId;
    }

    public void setDealSettingId(Long dealSettingId) {
        this.dealSettingId = dealSettingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DealMetadataDTO dealMetadataDTO = (DealMetadataDTO) o;

        if ( ! Objects.equals(id, dealMetadataDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DealMetadataDTO{" +
            "id=" + id +
            ", metaKey='" + metaKey + "'" +
            ", metaValue='" + metaValue + "'" +
            '}';
    }
}
