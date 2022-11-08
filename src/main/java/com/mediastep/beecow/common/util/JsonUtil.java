/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility class for testing REST controllers.
 */
@Slf4j
public class JsonUtil {

    /**
     * Convert an object to JSON byte array.
     *
     * @param object
     *            the object to convert
     * @return the JSON byte array
     * @throws IOException
     */
    public static String convertObjectToJsonString(Object object) {
        if (object == null) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            JavaTimeModule module = new JavaTimeModule();
            mapper.registerModule(module);
            return new String(mapper.writeValueAsBytes(object));
        }
        catch (IOException exc) {
            log.error("Can't convert object to string {}", exc.getMessage());
            return null;
        }
    }

    public static Object convertJsonStringToObject(String json, Class<?> classType) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(json, classType);
        }
        catch (IOException exc) {
            log.error("Can't convert json string to object {}", exc.getMessage());
            return null;
        }
    }

    public static <T> T convertFileContentToJsonObject(File jsonFile, Class<T> clazz) {
        if (!jsonFile.isFile()) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonFile, clazz);
        }
        catch (IOException exc) {
            log.error("Can't convert json file to object {}", exc.getMessage());
            return null;
        }
    }
}
