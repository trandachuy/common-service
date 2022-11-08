/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the MediaFile entity.
 */
@Data
@ToString
@EqualsAndHashCode(of = {"id"})
public class MediaFileDTO implements Serializable {

    private static final long serialVersionUID = 5666335639920662603L;

    private Long id;

    private Long imageId;

    @NotNull
    private String imageUUID;

    private String name;

    private String title;

    private Long content;

    private String path;

    private Integer delay;

    private Boolean status;

    private Integer width;

    private Integer height;

    private String urlPrefix;

    private String originFileName;

    private String extension;
}
