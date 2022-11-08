/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.dto;


import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediastep.beecow.common.domain.enumeration.DeviceTypeEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for the UserToken entity.
 */
public class UserDeviceTokenDTO implements Serializable {
	
	private static final long serialVersionUID = 7817072198695852471L;

	private Long id;
    
    @NotNull
    @ApiModelProperty(value = "Beecow user id", required = true)
    private Long userId;

    @NotNull
    @ApiModelProperty(value = "Device Token", required = true)
    private String deviceToken;
    
    private DeviceTypeEnum deviceType;
    
    @JsonIgnore
    private String endpointARN;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
    public DeviceTypeEnum getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceTypeEnum deviceType) {
        this.deviceType = deviceType;
    }

    public String getEndpointARN() {
		return endpointARN;
	}

	public void setEndpointARN(String endpointARN) {
		this.endpointARN = endpointARN;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDeviceTokenDTO userTokenDTO = (UserDeviceTokenDTO) o;

        if ( ! Objects.equals(id, userTokenDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserTokenDTO{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", deviceToken='" + deviceToken + "'" +
            ", deviceType='" + deviceType + "'" +
            '}';
    }
}
