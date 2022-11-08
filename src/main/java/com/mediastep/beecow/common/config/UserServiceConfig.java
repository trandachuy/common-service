/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.config;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mediastep.beecow.common.domain.enumeration.Gender;

/**
 * Config for UserResource and UserService
 */
@Configuration
public class UserServiceConfig {

    private static final String LANG_KEY_SEP = ",";

    private static final int DEFAULT_CREATE_GUEST_RETRY_TIME = 5;
    
    @Value("${beecow.gateway.userService.guest.createRetry:" + DEFAULT_CREATE_GUEST_RETRY_TIME + "}")
    private int retry;

    @Value("${beecow.gateway.userService.user.defaultLocation:}")
    private String defaultLocation;

    @Value("${beecow.gateway.userService.user.defaultLanguage:}")
    private String defaultLanguage;

    @Value("${beecow.gateway.userService.user.defaultAvatarUrl.default:}")
    private String defaultAvatarUrl;

    @Value("${beecow.gateway.userService.user.defaultAvatarUrl.male:}")
    private String defaultMaleAvatarUrl;

    @Value("${beecow.gateway.userService.user.defaultAvatarUrl.female:}")
    private String defaultFemaleAvatarUrl;

    private List<String> specialBeecowUserLangKeys;

    @Value("${beecow.gateway.userService.user.activationKeyValidTime:1}")
    private Long activationKeyValidTime;

    @Value("${beecow.gateway.userService.user.activationKeyValidTimeUnit:HOURS}")
    private ChronoUnit activationKeyValidTimeUnit;

    @Value("${beecow.gateway.userService.user.resetKeyValidTime:24}")
    private Long resetKeyValidTime;

    @Value("${beecow.gateway.userService.user.resetKeyValidTimeUnit:HOURS}")
    private ChronoUnit resetKeyValidTimeUnit;

    /**
     * @return the retry
     */
    public int getRetry() {
        return retry;
    }

    /**
     * @param retry the retry to set
     */
    public void setRetry(int retry) {
        this.retry = retry;
    }

    /**
     * @return the defaultLocation
     */
    public String getDefaultLocation() {
        return defaultLocation;
    }

    /**
     * @param defaultLocation the defaultLocation to set
     */
    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    /**
     * @return the defaultLanguage
     */
    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    /**
     * @param defaultLanguage the defaultLanguage to set
     */
    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    /**
     * @return the defaultAvatarUrl
     */
    public String getDefaultAvatarUrl(Gender gender) {
        if (Gender.MALE == gender) {
            return defaultMaleAvatarUrl;
        }
        else if (Gender.FEMALE == gender) {
            return defaultFemaleAvatarUrl;
        }
        else {
            return defaultAvatarUrl;
        }
    }

    /**
     * @return the specialBeecowUserLangKeys
     */
    public List<String> getSpecialBeecowUserLangKeys() {
        return specialBeecowUserLangKeys;
    }

    /**
     * @param specialBeecowUserLangKeys the specialBeecowUserLangKeys to set
     */
    @Value("${beecow.gateway.userService.beecowUser.specialBeecowUser.langKeys:}")
    public void setSpecialBeecowUserLangKeys(String specialBeecowUserLangKeys) {
        this.specialBeecowUserLangKeys = Arrays.asList(specialBeecowUserLangKeys.split(LANG_KEY_SEP));
    }

    /**
     * @return the activationKeyValidTime
     */
    public Long getActivationKeyValidTime() {
        return activationKeyValidTime;
    }

    /**
     * @param activationKeyValidTime the activationKeyValidTime to set
     */
    public void setActivationKeyValidTime(Long activationKeyValidTime) {
        this.activationKeyValidTime = activationKeyValidTime;
    }

    /**
     * @return the activationKeyValidTimeUnit
     */
    public ChronoUnit getActivationKeyValidTimeUnit() {
        return activationKeyValidTimeUnit;
    }

    /**
     * @param activationKeyValidTimeUnit the activationKeyValidTimeUnit to set
     */
    public void setActivationKeyValidTimeUnit(ChronoUnit activationKeyValidTimeUnit) {
        this.activationKeyValidTimeUnit = activationKeyValidTimeUnit;
    }

    /**
     * @return the resetKeyValidTime
     */
    public Long getResetKeyValidTime() {
        return resetKeyValidTime;
    }

    /**
     * @return the resetKeyValidTimeUnit
     */
    public ChronoUnit getResetKeyValidTimeUnit() {
        return resetKeyValidTimeUnit;
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.getValidator();
    }
}
