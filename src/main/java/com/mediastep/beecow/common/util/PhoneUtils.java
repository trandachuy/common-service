package com.mediastep.beecow.common.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.mediastep.beecow.common.dto.PhoneDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class PhoneUtils {
    public static final String PHONE_WITH_COUNTRY_CODE_FORMAT = "%s:%s";
    public static final String PHONE_WITH_COUNTRY_CODE_FORMAT_V2 = "+%s:%s";
    public static final String PHONE_WITH_COUNTRY_CODE_FORMAT_V3 = "+%s%s";
    public static final String DEFAULT_MOBILE_COUNTRY_CODE = "+84";
    public static final String $1 = ":";
    public static final String $2 = "+";
    public static final String $3 = ".+:";
    public static final String PHONE_PATTERN = "(\\+)?\\d{8,15}|^$";

    /**
     * @param countryCode: default "VN"
     */
    public static String refinePhoneNumber(String countryCode, String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)) return StringUtils.EMPTY;
        String refinePhoneNumber;
        String sCountryCode = !StringUtils.isEmpty(countryCode) ? countryCode : "VN";
        sCountryCode = sCountryCode.toUpperCase();
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(phoneNumber, sCountryCode);
            refinePhoneNumber = phoneUtil.format(swissNumberProto, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            refinePhoneNumber = StringUtils.EMPTY;
            log.error(e.getMessage());
        }
        return refinePhoneNumber;
    }

    public static Phonenumber.PhoneNumber refine(String phoneNumber, String countryCode) {
        Phonenumber.PhoneNumber res;
        if (StringUtils.isEmpty(phoneNumber)) return new Phonenumber.PhoneNumber();
        String sCountryCode = !StringUtils.isEmpty(countryCode) ? countryCode : "VN";
        sCountryCode = sCountryCode.toUpperCase();
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            res = phoneUtil.parse(phoneNumber, sCountryCode);
        } catch (NumberParseException e) {
            res = new Phonenumber.PhoneNumber();
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * @param countryCode: default "VN"
     */
    public static String getNationalNumber(String countryCode, String phoneNumber) {
        String nationalNumber;
        String countryCodeDefault = StringUtils.isEmpty(countryCode) ? "VN" : countryCode.toUpperCase();
        if (StringUtils.isEmpty(phoneNumber)) return StringUtils.EMPTY;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(phoneNumber, countryCodeDefault);
            return String.valueOf(swissNumberProto.getNationalNumber());
        } catch (NumberParseException e) {
            nationalNumber = StringUtils.EMPTY;
            log.error(e.getMessage());
        }
        return nationalNumber;
    }

    public static String buildPhoneWithCountryCode(String countryCode, String phone) {
        return String.format(PHONE_WITH_COUNTRY_CODE_FORMAT,
                StringUtils.defaultIfEmpty(countryCode, DEFAULT_MOBILE_COUNTRY_CODE),
                phone);
    }

    public static String buildPhoneWithCountryCodeV2(String countryCode, String phone) {
        return String.format(PHONE_WITH_COUNTRY_CODE_FORMAT_V2,
                StringUtils.defaultIfEmpty(countryCode, DEFAULT_MOBILE_COUNTRY_CODE),
                phone);
    }

    public static String getPhoneCode(String phoneNumber, String countryCode){
        String s = StringUtils.EMPTY;
        Phonenumber.PhoneNumber p = refine(phoneNumber, countryCode);
        if (Objects.equals(p.hasCountryCode(), true)){
            s = String.valueOf(p.getCountryCode());
        }
        return s;
    }

    public static PhoneDTO getPhoneDTO(String phone, String countryCode){
        if (StringUtils.isEmpty(phone)) return null;
        countryCode = StringUtils.isEmpty(countryCode) ? "VN" : countryCode;
        Phonenumber.PhoneNumber phoneNumber = refine(phone, countryCode);
        if (Objects.isNull(phoneNumber)) return null;
        if (String.valueOf(phoneNumber.getNationalNumber()).matches(PHONE_PATTERN)){
            String p = refinePhoneNumber(countryCode, String.format(PHONE_WITH_COUNTRY_CODE_FORMAT_V3, phoneNumber.getCountryCode(), phoneNumber.getNationalNumber()));
            PhoneDTO phoneDTO = PhoneDtoUtil.stringToPhoneDTO(p);
            if (Objects.nonNull(phoneDTO)){
                phoneDTO.setPhoneNumberWithPhoneCode(p);
            }
            return phoneDTO;
        }
        return null;
    }

    public static Set<String> getPhones(String phone, String countryCode) {
        if (StringUtils.isEmpty(phone)) return new HashSet<>();
        Set<String> phones = new HashSet<>();
        if (phone.contains($1)) {
            phone = phone.replaceAll($3, "");
        }
        String phoneCode = getPhoneCode(phone, countryCode);
        phones.add(phone);
        String p = refinePhoneNumber(countryCode, phone);
        phones.add(p);
        PhoneDTO phoneDTO = PhoneDtoUtil.stringToPhoneDTO(p);
        phones.add(getNationalNumber(countryCode, phone));
        phones.add(p.replace("+", ""));
        if (Objects.nonNull(phoneDTO)) {

            phones.add(phoneDTO.getPhoneNumberWithoutZero());
            phones.add(phoneDTO.getCountryCode() + phoneDTO.getPhoneNumberWithoutZero());
            phones.add(buildPhoneWithCountryCode(phoneCode, phoneDTO.getPhoneNumberWithoutZero()));

            phones.add(phoneDTO.getPhoneNumberWithZero());
            phones.add(phoneDTO.getCountryCode() + phoneDTO.getPhoneNumberWithZero());
            phones.add(buildPhoneWithCountryCode(phoneCode, phoneDTO.getPhoneNumberWithZero()));

            if (phoneDTO.getCountryCode().startsWith($2)){
                phones.add(buildPhoneWithCountryCode($2 + phoneCode, phoneDTO.getPhoneNumberWithoutZero()));
                phones.add(buildPhoneWithCountryCode($2 + phoneCode, phoneDTO.getPhoneNumberWithZero()));
            }

        }
        return phones;

    }

    public static void main(String[] args) {
        System.out.println(getPhones("+84:0394401476", ""));
    }
}


