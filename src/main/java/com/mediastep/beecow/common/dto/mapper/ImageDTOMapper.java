/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.dto.mapper;

import org.springframework.stereotype.Component;

import com.mediastep.beecow.common.dto.ImageDTO;
import com.mediastep.beecow.common.util.ImageDtoUtil;

/**
 * Mapper for the entity User and its DTO UserDTO.
 */
@Component
public class ImageDTOMapper {

    public ImageDTO imageURLToImageDTO(String imageURL) {
        return ImageDtoUtil.stringToImageDTO(imageURL);
    }

    public String imageDTOToImageURL(ImageDTO imageDTO) {
        return ImageDtoUtil.imageDTOToString(imageDTO);
    }
}
