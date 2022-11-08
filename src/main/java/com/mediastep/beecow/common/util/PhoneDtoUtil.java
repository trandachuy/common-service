/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *
 *******************************************************************************/
package com.mediastep.beecow.common.util;

import com.mediastep.beecow.common.dto.CountryDTO;
import com.mediastep.beecow.common.dto.PhoneDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Pattern;

public class PhoneDtoUtil {

    private static final Logger log = LoggerFactory.getLogger(PhoneDtoUtil.class);

    public static final String COUNTRY_CODE_SEP = ",";

    public static final String COUNTRY_CODE_PART_SEP = ":";

    public static final String COUNTRY_CODE_PREFIX = "+";

    public static final String COUNTRY_CODE_PREFIX2 = "00";

    public static final String PHONE_NUMBER_SEP = ":";

    private static final Map<String, String> PREDEFINED_COUNTRY_CODES = new ConcurrentHashMap<>();
    static {
        try {
            ResourceBundle phoneConf = ResourceBundle.getBundle("phone");
            String countryCodesAsString = phoneConf.getString("beecow.common.phone.country-codes");
            if (StringUtils.isNotBlank(countryCodesAsString)) {
                List<String> countryCodes = Arrays.asList(countryCodesAsString.split(Pattern.quote(COUNTRY_CODE_SEP)));
                String countryCodeSepPattern = Pattern.quote(COUNTRY_CODE_PART_SEP);
                for (String countryCode : countryCodes) {
                    String[] countryCodeParts = countryCode.split(countryCodeSepPattern);
                    PREDEFINED_COUNTRY_CODES.put(countryCodeParts[0], countryCodeParts[1]);
                }
            }
        }
        catch (Exception exc) {
            log.error("Failed to load country codes", exc);
        }
    }

    public static String getPredefinedPhoneCode(String countryCode, Function<String, String> defaultPhoneCodeFromCountryCode) {
        return PREDEFINED_COUNTRY_CODES.computeIfAbsent(countryCode, defaultPhoneCodeFromCountryCode);
    }

    public static void addPredefinedCountryCode(CountryDTO countryDTO) {
        PREDEFINED_COUNTRY_CODES.put(countryDTO.getCode(), countryDTO.getActualPhoneCode());
    }

    public static String toStringCallableFormat(String countryCode, String phoneNumber) {
        countryCode = StringUtils.trim(countryCode);
        phoneNumber = refinePhoneNumber(phoneNumber);
        return StringUtils.isBlank(phoneNumber) ? phoneNumber :
                StringUtils.startsWith(phoneNumber, "+") ? phoneNumber :
                        countryCode + phoneNumber;
    }

    public static String toStringCallableFormat(PhoneDTO phoneDTO) {
    	if (phoneDTO == null) {
    		return null;
    	}
        return toStringCallableFormat(phoneDTO.getCountryCode(), phoneDTO.getPhoneNumber());
    }

    public static String toString(String countryCode, String phoneNumber, boolean refinePhoneNumber) {
        countryCode = StringUtils.trim(countryCode);
        if (refinePhoneNumber) {
            phoneNumber = refinePhoneNumber(phoneNumber);
        }
        else {
            phoneNumber = StringUtils.trim(phoneNumber);
        }
        return countryCode + PHONE_NUMBER_SEP + phoneNumber;
    }

    public static String toString(String countryCode, String phoneNumber) {
        return toString(countryCode, phoneNumber, false);
    }

    public static String phoneDTOToString(PhoneDTO phoneDTO) {
        if (phoneDTO == null) {
            return null;
        }
        return toString(phoneDTO.getCountryCode(), phoneDTO.getPhoneNumber(), false);
    }

    public static String phoneDTOToRefinedString(PhoneDTO phoneDTO) {
        if (phoneDTO == null) {
            return null;
        }
        return toString(phoneDTO.getCountryCode(), phoneDTO.getPhoneNumber(), true);
    }

    public static PhoneDTO stringToPhoneDTO(String phone) {
        return stringToPhoneDTO(phone, false);
    }

    public static boolean hasCountryCode(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.startsWith(COUNTRY_CODE_PREFIX);
    }

    public static String addCountryCode(String phoneNumber, String countryCode) {
        if (hasCountryCode(phoneNumber)) {
            return refineRawPhoneNumber(phoneNumber);
        }
        String refinedPhoneNumber = phoneNumber;
        String phoneCountryCode = PREDEFINED_COUNTRY_CODES.get(countryCode);
        if (phoneCountryCode == null) {
            return refinedPhoneNumber;
        }
        else {
            return phoneCountryCode + refinedPhoneNumber;
        }
    }

    public static String removePhoneCode(String phoneNumber, String phoneCode) {
//        log.debug("Remove phone code {} from number {}", phoneCode, phoneNumber);
        if (StringUtils.isNotEmpty(phoneNumber) && StringUtils.isNotEmpty(phoneCode) && phoneNumber.startsWith(phoneCode)) {
            String refined = phoneNumber.substring(phoneCode.length());
            switch (phoneCode) {
                case "+84": //VN
                    refined = "0" + refined;
                    break;
                // TODO If any country have special require for inner phone number format, implement here
            }
            return refined;
        }
        return phoneNumber;
    }

    private static PhoneDTO unformulatedStringToPhoneDTO(String phone, boolean refinePhoneNumber) {
        phone = refineRawPhoneNumber(phone);
        String countryCode = "";
        String phoneNumber = "";
        for (String predefinedCountryCode : PREDEFINED_COUNTRY_CODES.values()) {
            if (phone.startsWith(predefinedCountryCode)) {
                countryCode = predefinedCountryCode;
                phoneNumber = removePhoneCode(phone, countryCode);
                break;
            }
        }
        return toPhoneDTO(countryCode, phoneNumber, refinePhoneNumber);
    }

    private static String refineRawPhoneNumber(String phoneNumber) {
        String refinedPhoneNumber = phoneNumber;
        // Trim of country code prefix
        if (refinedPhoneNumber.startsWith(COUNTRY_CODE_PREFIX)) {
            refinedPhoneNumber = refinedPhoneNumber.substring(COUNTRY_CODE_PREFIX.length());
        }
        else if (refinedPhoneNumber.startsWith(COUNTRY_CODE_PREFIX2)) {
            refinedPhoneNumber = refinedPhoneNumber.substring(COUNTRY_CODE_PREFIX2.length());
        }
        // Remove all not-number characters, and concatenate with standard country code prefix
        refinedPhoneNumber = COUNTRY_CODE_PREFIX + StringUtils.replacePattern(refinedPhoneNumber, "[^0-9]", "");
        return refinedPhoneNumber;
    }

    private static PhoneDTO formatedStringToPhoneDTO(String phone, boolean refinePhoneNumber) {
        String[] phoneNumberParts = phone.split(Pattern.quote(PHONE_NUMBER_SEP));
        String countryCode = StringUtils.trim(phoneNumberParts[0]);
        String phoneNumber;
        if (phoneNumberParts.length > 1) {
            phoneNumber = phoneNumberParts[1];
        }
        else {
            phoneNumber = "";
        }
        return toPhoneDTO(countryCode, phoneNumber, refinePhoneNumber);
    }

    private static PhoneDTO toPhoneDTO(String countryCode, String phoneNumber, boolean refinePhoneNumber) {
        if (refinePhoneNumber) {
            phoneNumber = refinePhoneNumber(phoneNumber);
        }
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setCountryCode(countryCode);
        phoneDTO.setPhoneNumber(phoneNumber);
        return phoneDTO;
    }

    public static PhoneDTO stringToPhoneDTO(String phone, boolean refinePhoneNumber) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        int sepIndex = phone.indexOf(PHONE_NUMBER_SEP);
        if (sepIndex < 0) {
            return unformulatedStringToPhoneDTO(phone, refinePhoneNumber);
        }
        else {
            return formatedStringToPhoneDTO(phone, refinePhoneNumber);
        }
    }

    public static String refinePhoneNumber(String phoneNumber) {
        phoneNumber = StringUtils.trim(phoneNumber);
        return StringUtils.stripStart(phoneNumber, "0");
    }
}
