/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.dto;

import java.io.Serializable;
import java.util.Objects;

import com.mediastep.beecow.common.dto.ImageDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * A DTO for the BcUserBasic entity.
 * @deprecated please user BcUserBasicDTO in user-shared
 */
@Deprecated
@ApiModel(value = "BcUserBasicDTO", description = "A DTO for user basic information entity")
public class BcUserBasicDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Unquine User ID.", required = true)
    private Long id;

    @ApiModelProperty(value = "User display name.", required = true)
    private String displayName;

    @ApiModelProperty(value = "URL of user avatar.")
    private ImageDTO avatar;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public ImageDTO getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageDTO avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BcUserBasicDTO bcUserBasicDTO = (BcUserBasicDTO) o;

        if ( ! Objects.equals(id, bcUserBasicDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BcUserBasicDTO{" +
            "id=" + id +
            ", displayName='" + displayName + "'" +
            ", avatar='" + avatar + "'" +
            '}';
    }
}
