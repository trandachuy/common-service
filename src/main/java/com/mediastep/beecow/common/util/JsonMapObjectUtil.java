/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.util;

import com.mediastep.beecow.common.domain.enumeration.AccountType;
import com.mediastep.beecow.common.domain.enumeration.Gender;
import com.mediastep.beecow.common.dto.ImageDTO;
import com.mediastep.beecow.common.dto.PhoneDTO;
import com.mediastep.beecow.common.errors.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.function.Function;

/**
 * The class provide helper methods to retrieve value type from input JSON object as Map&lt;String, Object&gt;.
 */
@Slf4j
public final class JsonMapObjectUtil {

    /**
     * Get field value as target type
     *
     * @param <T>            the type parameter
     * @param <V>            the type parameter
     * @param props          JSON object as Map&lt;String, Object&gt;
     * @param fieldName      field name
     * @param converter      converter method to convert input value to object (NOTE: input type of converter and validator must )
     * @param errorMessageId the error message id
     * @return value
     * @throws InvalidInputException if value cannot convert to target type
     */
    @SuppressWarnings("unchecked")
    public static <T, V> T getValue(Map<String, Object> props, String fieldName, Function<V, T> converter, String errorMessageId) {
        V value;
        try {
            value = (V) props.get(fieldName);
        }
        catch (ClassCastException exc) {
            throw new InvalidInputException(errorMessageId, exc);
        }
        if (value == null) {
            return null;
        }
        try {
            if (converter == null) {
                return (T) value;
            }
            else {
                return converter.apply(value);
            }
        }
        catch (RuntimeException exc) {
            throw new InvalidInputException(errorMessageId, exc, value.toString());
        }
    }

    /**
     * String to long
     *
     * @param value the value
     * @return long
     */
    public static Long toLong(Object value) {
        return Long.parseLong(value.toString());
    }

    /**
     * Convert to ZonedDateTime
     *
     * @param value the value
     * @return zoned date time
     */
    public static ZonedDateTime toZonedDateTime(String value) {
        return ZonedDateTime.parse(value);
    }

    /**
     * Convert to Gender
     *
     * @param value the value
     * @return gender
     */
    public static Gender toGender(String value) {
        return Gender.valueOf(value);
    }

    /**
     * Convert to AccountType
     *
     * @param value the value
     * @return account type
     */
    public static AccountType toAccountType(Object value) {
        if (value instanceof AccountType) {
            return (AccountType) value;
        }
        else {
            return AccountType.valueOf(value.toString());
        }
    }

    /**
     * Convert to ImageDTO helper
     *
     * @param props the props
     * @return image dto
     */
    public static ImageDTO toImageDTO(Map<String, Object> props) {
        log.debug("Current props {}", props);
        Long imageId = JsonMapObjectUtil.getValue(props, ImageDTO.IMAGE_ID, JsonMapObjectUtil::toLong, "");
        String imageUUID = JsonMapObjectUtil.getValue(props, ImageDTO.IMAGE_UUID, String::valueOf, "");
        String urlPrefix = JsonMapObjectUtil.getValue(props, ImageDTO.URL_PREFIX, String::valueOf, "");
        String extension = JsonMapObjectUtil.getValue(props, ImageDTO.EXTENSION, String::valueOf, "");
        log.debug("Result imageId {}, imageUUID {}, urlPrefix {}, extension {}", imageId, imageUUID, urlPrefix, extension);
        ImageDTO imageDTO = ImageDTO.builder().imageId(imageId).imageUUID(imageUUID).urlPrefix(urlPrefix).extension(extension).build();
//        imageDTO.setImageUUID(imageUUID);
//        imageDTO.setImageId(imageId);
//        imageDTO.setUrlPrefix(urlPrefix);
        log.debug("Result imageDTO {}", imageDTO);
        return imageDTO;
    }

    /**
     * Convert to ImageDTO helper
     *
     * @param props the props
     * @return string
     */
    public static String toImageAsString(Map<String, Object> props) {
        ImageDTO imageDTO = toImageDTO(props);
        log.debug("ImageDTO result {} extract from props {}", imageDTO, props);
        return ImageDtoUtil.imageDTOToString(imageDTO);
    }

    /**
     * Convert to PhoneDTO helper
     *
     * @param props the props
     * @return phone dto
     */
    public static PhoneDTO toPhoneDTO(Map<String, Object> props) {
        String countryCode = JsonMapObjectUtil.getValue(props, PhoneDTO.COUNTRY_CODE, String::valueOf, "");
        countryCode = StringUtils.trim(countryCode);
        String phoneNumber = JsonMapObjectUtil.getValue(props, PhoneDTO.PHONE_NUMBER, String::valueOf, "");
        phoneNumber = StringUtils.trim(phoneNumber);
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setCountryCode(countryCode);
        phoneDTO.setPhoneNumber(phoneNumber);
        return phoneDTO;
    }

    /**
     * Convert to PhoneDTO helper
     *
     * @param props the props
     * @return string
     */
    public static String toPhoneAsString(Map<String, Object> props) {
        PhoneDTO phoneDTO = toPhoneDTO(props);
        return PhoneDtoUtil.phoneDTOToString(phoneDTO);
    }
}
