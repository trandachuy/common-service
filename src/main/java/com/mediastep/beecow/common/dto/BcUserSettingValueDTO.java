/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the BcUserSettingValue entity.
 */
public class BcUserSettingValueDTO extends AbstractAuditingDTO implements Serializable {

    private static final long serialVersionUID = -3846716954854515272L;

    private Long id;

    private Object settingValue;


    private Long bcUserId;
    
    private Long settingId;
    
    private String settingKey;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Object getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(Object settingValue) {
        this.settingValue = settingValue;
    }

    public Long getBcUserId() {
        return bcUserId;
    }

    public void setBcUserId(Long bcUserId) {
        this.bcUserId = bcUserId;
    }

    public Long getSettingId() {
        return settingId;
    }

    public void setSettingId(Long bcUserSettingId) {
        this.settingId = bcUserSettingId;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BcUserSettingValueDTO bcUserSettingValueDTO = (BcUserSettingValueDTO) o;

        if ( ! Objects.equals(id, bcUserSettingValueDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BcUserSettingValueDTO{" +
            "id=" + id +
            ", settingValue='" + settingValue + "'" +
            '}';
    }
}
