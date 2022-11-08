/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 17/9/2018
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.security;

import org.springframework.security.core.Authentication;

/**
 * The interface Token provider.
 */
public interface ITokenProvider {

    /**
     * Create token string.
     *
     * @param authentication the authentication
     * @param isRemember     the is remember
     * @return the string
     */
    String createToken(Authentication authentication, Boolean isRemember);

    /**
     * Gets authentication.
     *
     * @param token the token
     * @return the authentication
     */
    Authentication getAuthentication(String token);

    /**
     * Gets authentication no throws.
     *
     * @param token the token
     * @return the authentication no throws
     */
    Authentication getAuthenticationNoThrows(String token);

    /**
     * Validate token boolean.
     *
     * @param authToken the auth token
     * @return the boolean
     */
    boolean validateToken(String authToken);
}
