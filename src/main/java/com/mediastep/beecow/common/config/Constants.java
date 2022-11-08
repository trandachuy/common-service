/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9+-]*$";
    public static final String EMAIL_REGEX = "^.+@.+\\.?.*$";
    public static final String PHONE_COUNTRY_CODE_REGEX = "^\\+[1-9][0-9]*$";
    public static final String PHONE_NUMBER_REGEX = "^[0-9]+$";
    public static final String PHONE_NUMBER_WITH_OPTIONAL_LEADING_PLUS = "^\\+?\\d+$";
    // Spring profiles for development, test and production, see http://jhipster.github.io/profiles/
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_TEST = "test";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    // Spring profile used when deploying to Heroku
    public static final String SPRING_PROFILE_HEROKU = "heroku";
    // Spring profile used to disable swagger
    public static final String SPRING_PROFILE_SWAGGER = "swagger";
    // Spring profile used to disable running liquibase
    public static final String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";

    public static final String SYSTEM_ACCOUNT = "system";

    public static final int CURRENCY_PRECISION = 20;
    public static final int CURRENCY_SCALE = 8;

    public static final int PERSENTAGE_PRECISION = 5;
    public static final int PERSENTAGE_SCALE = 2;
    
    public static final String ALL_COUNTRY = "all";

    private Constants() {
    }
}
