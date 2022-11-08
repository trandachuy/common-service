/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediastep.beecow.common.dto.ImageDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;

/**
 * Util to convert image URL from String to ImageDTO
 *
 * @author huyen
 */
@Slf4j
public class ImageDtoUtil {

    /**
     * The constant EXT_JPG.
     */
    public static final String EXT_JPG = ".jpg";

    /**
     * The constant URL_SEP.
     */
    public static final String URL_SEP = "/";

    /**
     * The constant EXT_SEP.
     */
    public static final String EXT_SEP = ".";

    public static final String SCHEME_BLOB = "blob";

    /**
     * Convert from String to ImageDTO
     *
     * @param imageUrl the image url
     * @return <pre> NOTE: + Image name must be NUMBER. e,g. http://qa-media-mediastep.s3.amazonaws.com/123456789 + In case input image name is not number then: <code>imageId = null</code> and <code>urlPrefix = imageUrl</code> </pre>
     */
    @SneakyThrows
    public static ImageDTO stringToImageDTO(String imageUrl) {
        if (imageUrl == null) {
            return null;
        }
        if (imageUrl.startsWith(SCHEME_BLOB)) {
            return ImageDTO.builder().urlPrefix(imageUrl).build();
        }
        String urlPrefix, imageName, imageUUID = null, extension = null;
        Long imageId = null;
        int seperateIndex = imageUrl.lastIndexOf(URL_SEP);
        if (seperateIndex > -1) {
            URI uri = new URI(imageUrl);
            urlPrefix = uri.getScheme() + "://" + uri.getHost();

            imageName = uri.getPath();
            int extensionIndex = imageName.lastIndexOf(EXT_SEP);
            if (extensionIndex > -1) {
                imageUUID = imageName.substring(1, extensionIndex); // Ignore 1st "/" character of path
                extension = imageName.substring(extensionIndex + 1);
            }
            else {
                imageUUID = imageName.replace("/", "");
            }
            try {
                imageId = Long.parseLong(imageUUID);
                imageUUID = null;
            }
            catch (NumberFormatException e) {
                log.trace("Image UUID '{}' is not a number", imageUUID);
            }
        }
        else {
            urlPrefix = imageUrl;
        }
        return ImageDTO.builder().imageId(imageId).imageUUID(imageUUID).extension(extension).urlPrefix(urlPrefix).build();
    }

    /**
     * Convert from ImageDTO to String
     *
     * @param imageDto the image dto
     * @return string
     */
    public static String imageDTOToString(ImageDTO imageDto) {
        return imageDTOToStringWithSize(imageDto, null);
    }

    /**
     * Image dto to string with size string.
     *
     * @param imageDto the image dto
     * @param size     the size
     * @return the string
     */
    public static String imageDTOToStringWithSize(ImageDTO imageDto, Integer size) {
        return imageDTOToStringWithWidthAndHeight(imageDto, size, null);
    }

    /**
     * Image dto to string with width and height string.
     *
     * @param imageDto the image dto
     * @param width    the width
     * @param height   the height
     * @return the string
     */
    public static String imageDTOToStringWithWidthAndHeight(ImageDTO imageDto, Integer width, Integer height) {
        if (imageDto == null) {
            return null;
        }
        StringBuilder imageUrl;
        if (imageDto.getUrlPrefix() == null) {
            imageUrl = new StringBuilder();
        }
        else {
            imageUrl = new StringBuilder(imageDto.getUrlPrefix());
            if (!imageDto.getUrlPrefix().endsWith(URL_SEP)) {
                imageUrl.append(URL_SEP);
            }
        }

        if (width != null) {
            if (height != null) {
                imageUrl.append(width).append("x").append(height);
            }
            else {
                imageUrl.append(width);
            }
            imageUrl.append(URL_SEP);
        }

        String imageName = "", imageUUID = imageDto.getImageUUID();
        if (StringUtils.isNotBlank(imageUUID)) {
            if (imageUUID.startsWith("/")) {
                imageUUID = StringUtils.stripStart(imageUUID, "/");
            }
            imageName = imageUUID;
        }
        else if (imageDto.getImageId() != null) {
            imageName = imageDto.getImageId().toString();
        }
        imageUrl.append(imageName);
        if (StringUtils.isNotBlank(imageDto.getExtension())) {
            imageUrl.append(EXT_SEP).append(imageDto.getExtension());
        }
        else {
            imageUrl.append(EXT_JPG);
        }
        return imageUrl.toString();
    }

    /**
     * Convert from ImageDTO to String
     *
     * @param urlPrefix the url prefix
     * @param imageId   the image id
     * @return string
     */
    @Deprecated
    public static String imageDTOToString(String urlPrefix, Long imageId) {
        throw new UnsupportedOperationException("Method not be supported anymore");
    }

    public static ImageDTO convertToImageDTO(Object obj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            byte[] dataByte = objectMapper.writeValueAsBytes(obj);
            return objectMapper.readValue(dataByte, ImageDTO.class);
        } catch (IOException e) {
            throw new IOException("Input object must include properties: urlPrefix, imageUUID or ImageId, extension (optional)", e);
        }
    }

    public static String scaleImageWithSize(String rawImage, Integer size) {
        return ImageDtoUtil.imageDTOToStringWithSize(ImageDtoUtil.stringToImageDTO(rawImage), size);
    }

    public static String scaleImageWithWidthAndHeight(String rawImage, Integer width, Integer height) {
        return ImageDtoUtil.imageDTOToStringWithWidthAndHeight(ImageDtoUtil.stringToImageDTO(rawImage), width, height);
    }
}
