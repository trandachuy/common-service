/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.mediastep.beecow.common.config.Constants;

@Component
public class SystemTokenProviderService {

    @Inject
    private ITokenProvider tokenProvider;

    private String token = null;

    private String bearerToken = null;

    public synchronized String getSystemBearerToken() {
        if (!validateToken()) {
            createToken();
        }
        return bearerToken;
    }

    public synchronized String getSystemToken() {
        if (!validateToken()) {
            createToken();
        }
        return token;
    }

    private boolean validateToken() {
        return (token != null && tokenProvider.validateToken(token));
    }

    private void createToken() {
        token = createSystemToken();
        bearerToken = "Bearer " + token;
    }

    private String createSystemToken() {
        TokenUserAuthority authority = new TokenUserAuthority(AuthoritiesConstants.ADMIN);
        Set<TokenUserAuthority> authorities = new HashSet<>(Arrays.asList(authority));
        ITokenUserDetails tokenUser = TokenDetails.builder().username(Constants.SYSTEM_ACCOUNT).activated(true).authorities(authorities).build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(tokenUser, null, authorities);
        String token = tokenProvider.createToken(authentication, true);
        return token;
    }
}
