/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 10/4/2017
 * Author: Quang Huynh <email:quang.huynh@mediastep.com>
 *
 */

package com.mediastep.beecow.common.dto;

import com.mediastep.beecow.common.util.ImageDtoUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for storing image information
 */
@ApiModel(value = "ImageDTO", description = "ImageDTO")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ImageDTO implements Serializable {

    public static final String IMAGE_ID = "imageId";
    public static final String URL_PREFIX = "urlPrefix";
    public static final String IMAGE_UUID = "imageUUID";
    public static final String EXTENSION = "extension";

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Image ID")
    @Deprecated
    private Long imageId;

    @ApiModelProperty(value = "Image name (exclude extension)")
    @NotNull
    private String imageUUID;

    @ApiModelProperty(value = "Image Url Prefix")
    @NotNull
    private String urlPrefix;

    private String extension;

    @Builder
    public ImageDTO(Long imageId, @NotNull String imageUUID, @NotNull String urlPrefix, String extension) {
        this.imageId = imageId;
        this.imageUUID = imageUUID;
        this.urlPrefix = urlPrefix;
        this.extension = extension;
    }

    public String getFullUrl() {
        return ImageDtoUtil.imageDTOToString(this);
    }
}
