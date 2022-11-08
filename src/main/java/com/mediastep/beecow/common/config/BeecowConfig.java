/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Config for UserResource and UserService
 */
@Configuration
@Data
public class BeecowConfig {

    private static final long DEFAULT_TOKEN_VALIDITY = 604800L; // 7 days

    private static final long DEFAULT_TOKEN_VALIDITY_FOR_REMEMBER_ME = 2592000L; // 30 days

    private static final String DEFAULT_LANGUAGE = "en";

    private static final String DEFAULT_LANGUAGES = "en,vi";

    private static final long DEFAULT_STAFF_TOKEN_VALIDITY = 5 * 60 * 1000 ;// 5 minutes

    private static final long DEFAULT_GOSELL_STORE_TOKEN_VALIDITY = 24 * 60 * 60 * 1000; // 1 day

    @Value("${beecow.debugMode:false}")
    private boolean debugMode;

    @Value("${beecow.system.username:}")
    private String systemUsername;

    @Value("${beecow.system.password:}")
    private String systemPassword;

    @Value("${jhipster.security.authentication.jwt.secret}")
    private String jwtSecretKey;

    @Value("${jhipster.security.authentication.jwt.tokenValidityInSeconds:" + DEFAULT_TOKEN_VALIDITY + "}")
    private Long jwtTokenValidity;

    @Value("${jhipster.security.authentication.jwt.tokenValidityInSecondsForRememberMe:" + DEFAULT_TOKEN_VALIDITY_FOR_REMEMBER_ME + "}")
    private Long jwtTokenValidityForRememberMe;

    private List<String> languages = new ArrayList<>();

    @Value("${beecow.defaultLanguage:" + DEFAULT_LANGUAGE + "}")
    private String defaultLanguage;

    @Value("${gosell.security.authentication.jwt.staffTokenValidityInSeconds:" + DEFAULT_STAFF_TOKEN_VALIDITY + "}")
    private Long jwtGsStaffTokenValidity;

    @Value("${gosell.security.authentication.jwt.staffTokenValidityInSecondsForRememberMe:" + DEFAULT_STAFF_TOKEN_VALIDITY + "}")
    private Long jwtGsStaffTokenValidityForRememberMe;

    @Value("${gosell.security.authentication.jwt.storeTokenValidityInSeconds:" + DEFAULT_GOSELL_STORE_TOKEN_VALIDITY + "}")
    private Long jwtGsStoreTokenValidity;

    @Value("${gosell.security.authentication.jwt.storeTokenValidityInSecondsForRememberMe:" + DEFAULT_GOSELL_STORE_TOKEN_VALIDITY + "}")
    private Long jwtGsStoreTokenValidityForRememberMe;

    /**
     * @return the value of "beecow.debugMode" in the configuration
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * @return the value of "beecow.system.username" in the configuration
     */
    public String getSystemUsername() {
        return systemUsername;
    }

    /**
     * @param systemUsername the systemUsername to set
     */
    public void setSystemUsername(String systemUsername) {
        this.systemUsername = systemUsername;
    }

    /**
     * @return the value of "beecow.system.password" in the configuration
     */
    public String getSystemPassword() {
        return systemPassword;
    }

    /**
     * @param systemPassword the systemPassword to set
     */
    public void setSystemPassword(String systemPassword) {
        this.systemPassword = systemPassword;
    }

    /**
     * @return the value of "jhipster.security.authentication.jwt.secret" in the configuration
     */
    public String getJwtSecretKey() {
        return jwtSecretKey;
    }

    /**
     * @param jwtSecretKey the jwtSecretKey to set
     */
    public void setJwtSecretKey(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    /**
     * @return the value of "jhipster.security.authentication.jwt.tokenValidityInSeconds" in the configuration
     */
    public long getJwtTokenValidity() {
        return jwtTokenValidity;
    }

    /**
     * @param jwtTokenValidity the jwtTokenValidity to set
     */
    public void setJwtTokenValidity(long jwtTokenValidity) {
        this.jwtTokenValidity = jwtTokenValidity;
    }

    /**
     * @return the value of "jhipster.security.authentication.jwt.tokenValidityInSecondsForRememberMe" in the configuration
     */
    public long getJwtTokenValidityForRememberMe() {
        return jwtTokenValidityForRememberMe;
    }

    /**
     * @param jwtTokenValidityForRememberMe the jwtTokenValidityForRememberMe to set
     */
    public void setJwtTokenValidityForRememberMe(long jwtTokenValidityForRememberMe) {
        this.jwtTokenValidityForRememberMe = jwtTokenValidityForRememberMe;
    }

	/**
	 * Get supported langauges.
	 */
	public List<String> getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
    @Value("${beecow.languages:" + DEFAULT_LANGUAGES + "}")
	public void setLanguages(String languages) {
		if (StringUtils.isBlank(languages)) {
			this.languages = new ArrayList<>();
		}
		else {
			this.languages = Arrays.asList(languages.split(Pattern.quote(",")));
		}
	}

    /**
	 * Get default language.
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

	public Locale getLocale(String language) {
        if (StringUtils.isBlank(language) || !languages.contains(StringUtils.lowerCase(language))) {
            language = defaultLanguage;
        }
        return Locale.forLanguageTag(language);
    }
}
