/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mediastep.beecow.common.security.Token;
import io.swagger.annotations.ApiModel;

/**
 * A DTO representing a user, with his authorities.
 */
@Deprecated
@ApiModel(value = "AuthUserDTO", description = "A DTO of user entity and access token")
public class AuthUserDTO extends UserDTO implements Token {

    private TokenDTO token = new TokenDTO();

    private Map<String, Object> settings = new HashMap<>();

    public AuthUserDTO() {
        super();
    }

    public AuthUserDTO(String login, String firstName, String lastName,
        String email, boolean activated, String langKey, Set<String> authorities) {
        super(login, firstName, lastName, email, activated, langKey, authorities);
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return token.getAccessToken();
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        token.setAccessToken(accessToken);
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return token.getRefreshToken();
    }

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(String refreshToken) {
        token.setRefreshToken(refreshToken);
    }

    /**
     * @return the settings
     */
    public Map<String, Object> getSettings() {
        return settings;
    }

    /**
     * @param settings the settings to set
     */
    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }

}
