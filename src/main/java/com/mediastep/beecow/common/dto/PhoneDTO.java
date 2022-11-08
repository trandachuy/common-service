/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 10/4/2017
 * Author: Quang Huynh <email:quang.huynh@mediastep.com>
 *
 */

package com.mediastep.beecow.common.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mediastep.beecow.common.config.Constants;
import com.mediastep.beecow.common.util.PhoneDtoUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A DTO for storing phone number
 */
public class PhoneDTO implements Serializable {

    public static final String COUNTRY_CODE = "countryCode";
    public static final String PHONE_NUMBER = "phoneNumber";

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(
        name = "countryCode",
        value = "Country dial-in codes"
    )
    @NotNull
    @Pattern(regexp = Constants.PHONE_COUNTRY_CODE_REGEX)
    @Size(min = 2, max = 5)
    private String countryCode;

    @ApiModelProperty(value = "Phone number")
    @NotNull
    @Pattern(regexp = Constants.PHONE_NUMBER_REGEX)
    @Size(min = 8, max = 20)
    private String phoneNumber;

    @JsonIgnore
    private String phoneNumberWithZero = null;

    @JsonIgnore
    private String phoneNumberWithoutZero = null;

    private String phoneNumberWithPhoneCode = null;

    /**
     * @return the countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        phoneNumberWithoutZero = PhoneDtoUtil.refinePhoneNumber(phoneNumber);
        phoneNumberWithZero = "0" + phoneNumberWithoutZero;
    }

    /**
     * @return the phoneNumberWithZero
     */
    public synchronized String getPhoneNumberWithZero() {
        return phoneNumberWithZero;
    }

    /**
     * @return the phoneNumberWithoutZero
     */
    public synchronized String getPhoneNumberWithoutZero() {
        return phoneNumberWithoutZero;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
        result = prime * result + ((phoneNumberWithoutZero == null) ? 0 : phoneNumberWithoutZero.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PhoneDTO other = (PhoneDTO) obj;
        if (countryCode == null) {
            if (other.countryCode != null)
                return false;
        } else if (!countryCode.equals(other.countryCode))
            return false;
        if (phoneNumberWithoutZero == null) {
            if (other.phoneNumberWithoutZero != null)
                return false;
        } else if (!phoneNumberWithoutZero.equals(other.phoneNumberWithoutZero))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return PhoneDtoUtil.toString(countryCode, phoneNumber);
    }

    public String toStringFromPhoneWithZero() {
        return PhoneDtoUtil.toString(countryCode, phoneNumberWithZero);
    }

    public String toStringFromPhoneWithoutZero() {
        return PhoneDtoUtil.toString(countryCode, phoneNumberWithoutZero);
    }

    public String toStringCallableFormat() {
    	return PhoneDtoUtil.toStringCallableFormat(countryCode, phoneNumber);
    }

    public String getPhoneNumberWithPhoneCode() {
        return phoneNumberWithPhoneCode;
    }

    public void setPhoneNumberWithPhoneCode(String phoneNumberWithPhoneCode) {
        this.phoneNumberWithPhoneCode = phoneNumberWithPhoneCode;
    }
}
