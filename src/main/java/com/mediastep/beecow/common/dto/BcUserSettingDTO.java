/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.dto;

import com.mediastep.beecow.common.domain.enumeration.DataType;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BcUserSetting entity.
 */
public class BcUserSettingDTO extends AbstractAuditingDTO implements Serializable {

    private static final long serialVersionUID = -3846716954854515272L;

    private Long id;

    private String settingKey;

    private DataType settingType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }
    public DataType getSettingType() {
        return settingType;
    }

    public void setSettingType(DataType settingType) {
        this.settingType = settingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BcUserSettingDTO bcUserSettingDTO = (BcUserSettingDTO) o;

        if ( ! Objects.equals(id, bcUserSettingDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BcUserSettingDTO{" +
            "id=" + id +
            ", settingKey='" + settingKey + "'" +
            ", settingType='" + settingType + "'" +
            '}';
    }
}
