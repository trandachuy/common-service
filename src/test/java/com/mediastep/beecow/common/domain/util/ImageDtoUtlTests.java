/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.mediastep.beecow.common.BeecowCommonApplication;
import com.mediastep.beecow.common.dto.ImageDTO;
import com.mediastep.beecow.common.util.ImageDtoUtil;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BeecowCommonApplication.class})
public class ImageDtoUtlTests extends AbstractDataTypeMapperTests {

    private static final Long IMAGE_ID = 123456L;

    private static final String IMAGE_HOST = "http://amazone.com";

    private static final String IMAGE_PREFIX_URL = IMAGE_HOST + "/location";

    private static final String IMAGE_URL = IMAGE_PREFIX_URL + "/" + IMAGE_ID;
    
    private static final String IMAGE_UUID = "uuid";

    private static final String IMAGE_EXTENSION = "gif";

    @Test
    public void stringToImageDTO() {
        ImageDTO imageDTO = ImageDtoUtil.stringToImageDTO(IMAGE_URL);
        assertThat(imageDTO.getUrlPrefix()).isEqualTo(IMAGE_PREFIX_URL);
        assertThat(imageDTO.getImageId()).isEqualTo(IMAGE_ID);
    }

    @Test
    public void stringToImageDTONoSep() {
        String url = "amazone.com";
        ImageDTO imageDTO = ImageDtoUtil.stringToImageDTO(url);
        assertThat(imageDTO.getUrlPrefix()).isEqualTo(url);
        assertThat(imageDTO.getImageId()).isNull(); // Invalid image
    }

    @Test
    public void stringToImageDTOBlank() {
        String url = "    ";
        ImageDTO imageDTO = ImageDtoUtil.stringToImageDTO(url);
        assertThat(imageDTO.getUrlPrefix()).isEqualTo(url);
        assertThat(imageDTO.getImageId()).isNull(); // Invalid image
    }

    @Test
    public void stringToImageDTOEmpty() {
        String url = "";
        ImageDTO imageDTO = ImageDtoUtil.stringToImageDTO(url);
        assertThat(imageDTO.getUrlPrefix()).isEmpty();
        assertThat(imageDTO.getImageId()).isNull(); // Invalid image
    }

    @Test
    public void stringToImageDTONull() {
        String url = null;
        ImageDTO imageDTO = ImageDtoUtil.stringToImageDTO(url);
        assertThat(imageDTO).isNull();
    }

    @Test
    public void stringToImageDTOHaveUUID() {
        String url = IMAGE_PREFIX_URL + "/" + IMAGE_UUID;
        ImageDTO imageDTO = ImageDtoUtil.stringToImageDTO(url);
        assertThat(imageDTO.getUrlPrefix()).isEqualTo(IMAGE_PREFIX_URL);
        assertThat(imageDTO.getImageId()).isNull();
        assertThat(imageDTO.getImageUUID()).isEqualTo(IMAGE_UUID);
    }

    @Test
    public void stringToImageDTOHaveExtension() {
        String url = IMAGE_PREFIX_URL + "/" + IMAGE_UUID + "." + IMAGE_EXTENSION;
        ImageDTO imageDTO = ImageDtoUtil.stringToImageDTO(url);
        assertThat(imageDTO.getUrlPrefix()).isEqualTo(IMAGE_PREFIX_URL);
        assertThat(imageDTO.getImageId()).isNull();
        assertThat(imageDTO.getImageUUID()).isEqualTo(IMAGE_UUID);
        assertThat(imageDTO.getExtension()).isEqualTo(IMAGE_EXTENSION);
    }

    @Test
    public void imageDTOToString() {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setImageId(IMAGE_ID);
        imageDTO.setUrlPrefix(IMAGE_PREFIX_URL);
        String imageUrl = ImageDtoUtil.imageDTOToString(imageDTO);
        assertThat(imageUrl).isEqualTo(IMAGE_URL);
    }

    @Test
    public void imageDTOToStringUrlPrefixHasSep() {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setImageId(IMAGE_ID);
        imageDTO.setUrlPrefix(IMAGE_PREFIX_URL + "/"); // prefix as "/" as the end
        String imageUrl = ImageDtoUtil.imageDTOToString(imageDTO);
        assertThat(imageUrl).isEqualTo(IMAGE_URL);
    }

    @Test
    public void imageDTOToStringUrlPrefixBlank() {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setImageId(IMAGE_ID);
        String urlPrefix = "    ";
        imageDTO.setUrlPrefix(urlPrefix);
        String imageUrl = ImageDtoUtil.imageDTOToString(imageDTO);
        assertThat(imageUrl).isEqualTo(urlPrefix + "/" + IMAGE_ID);
    }

    @Test
    public void imageDTOToStringUrlPrefixEmpty() {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setImageId(IMAGE_ID);
        String urlPrefix = "";
        imageDTO.setUrlPrefix(urlPrefix);
        String imageUrl = ImageDtoUtil.imageDTOToString(imageDTO);
        assertThat(imageUrl).isEqualTo(urlPrefix + "/" + IMAGE_ID);
    }

    @Test
    public void imageDTOToStringUrlPrefixNull() {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setImageId(IMAGE_ID);
        String urlPrefix = null;
        imageDTO.setUrlPrefix(urlPrefix);
        String imageUrl = ImageDtoUtil.imageDTOToString(imageDTO);
        assertThat(imageUrl).isEqualTo(IMAGE_ID.toString());
    }

//    @Test
//    public void imageDTOToStringImageIdBlank() {
//        ImageDTO imageDTO = new ImageDTO();
//        String imageId = "    ";
//        imageDTO.setImageId(imageId);
//        imageDTO.setUrlPrefix(IMAGE_PREFIX_URL);
//        String imageUrl = ImageDtoUtil.imageDTOToString(imageDTO);
//        assertThat(imageUrl).isEqualTo(IMAGE_PREFIX_URL + "/" + imageId);
//    }

//    @Test
//    public void imageDTOToStringImageIdEmpty() {
//        ImageDTO imageDTO = new ImageDTO();
//        String imageId = "";
//        imageDTO.setImageId(imageId);
//        imageDTO.setUrlPrefix(IMAGE_PREFIX_URL);
//        String imageUrl = ImageDtoUtil.imageDTOToString(imageDTO);
//        assertThat(imageUrl).isEqualTo(IMAGE_PREFIX_URL + "/" + imageId);
//    }

    @Test
    public void imageDTOToStringImageIdNull() {
        ImageDTO imageDTO = new ImageDTO();
        Long imageId = null;
        imageDTO.setImageId(imageId);
        imageDTO.setUrlPrefix(IMAGE_PREFIX_URL);
        String imageUrl = ImageDtoUtil.imageDTOToString(imageDTO);
        assertThat(imageUrl).isEqualTo(IMAGE_PREFIX_URL + "/");
    }

    @Test
    public void imageDTOToStringWithImageDTONull() {
        ImageDTO imageDTO = null;
        String imageUrl = ImageDtoUtil.imageDTOToString(imageDTO);
        assertThat(imageUrl).isNull();
    }

    @Test
    public void imageDTOToStringWithWidthAndHeight() {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setImageId(IMAGE_ID);
        imageDTO.setUrlPrefix(IMAGE_PREFIX_URL);
        String imageUrl = ImageDtoUtil.imageDTOToStringWithSize(imageDTO, null);
        assertThat(imageUrl).isEqualTo(IMAGE_URL);
    }
}
