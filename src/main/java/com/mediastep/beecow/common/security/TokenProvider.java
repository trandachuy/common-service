/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.mediastep.beecow.common.errors.UnauthorizedException;
import io.jsonwebtoken.*;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.mediastep.beecow.common.config.BeecowConfig;
import com.mediastep.beecow.common.domain.enumeration.AuthorTypeEnum;

/**
 * The type Token provider.
 */
@Component("beecowTokenProvider")
@Data
@Deprecated
public class TokenProvider implements ITokenProvider {

    /**
     * The constant AUTHORITIES_KEY.
     */
    public static final String AUTHORITIES_KEY = "auth";

    /**
     * The constant DISPLAY_NAME_KEY.
     */
    public static final String DISPLAY_NAME_KEY = "displayName";

    /**
     * The constant USER_ID_KEY.
     */
    public static final String USER_ID_KEY = "userId";

    /**
     * The constant LOCATION_CODE_KEY.
     */
    public static final String LOCATION_CODE_KEY = "locationCode";

    /**
     * The constant STORE_ID_KEY.
     */
    public static final String STORE_ID_KEY = "storeId";

    /**
     * The constant PAGE_ID_KEY.
     */
    public static final String PAGE_ID_KEY = "pageId";

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private String secretKey;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    /**
     * Instantiates a new Token provider.
     *
     * @param beecowConfig the beecow config
     */
    public TokenProvider(BeecowConfig beecowConfig) {
        secretKey = beecowConfig.getJwtSecretKey();
        tokenValidityInMilliseconds = beecowConfig.getJwtTokenValidity() * 1000;
        tokenValidityInMillisecondsForRememberMe = beecowConfig.getJwtTokenValidityForRememberMe() * 1000;
    }

    /**
     * Create token string.
     *
     * @param authentication the authentication
     * @param rememberMe     the remember me
     * @return the string
     */
    public String createToken(Authentication authentication, Boolean rememberMe) {
        return this.createToken(authentication, rememberMe, AuthorTypeEnum.USER);
    }

    /**
     * Create token string.
     *
     * @param authentication the authentication
     * @param rememberMe     the remember me
     * @param role           the role
     * @return the string
     */
    public String createToken(Authentication authentication, Boolean rememberMe, AuthorTypeEnum role) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(authentication.getName());
        switch (role) {
            case STORE:
                TokenStoreDetails store = (TokenStoreDetails)authentication.getPrincipal();
                jwtBuilder.claim(AUTHORITIES_KEY, authorities)
                .claim(DISPLAY_NAME_KEY, store.getDisplayName())
                .claim(USER_ID_KEY, store.getId())
                .claim(STORE_ID_KEY, store.getStoreId())
                .claim(LOCATION_CODE_KEY, store.getLocationCode());
                break;
            case COMPANY:
                TokenPageDetails company = (TokenPageDetails)authentication.getPrincipal();
                jwtBuilder.claim(AUTHORITIES_KEY, authorities)
                .claim(DISPLAY_NAME_KEY, company.getDisplayName())
                .claim(USER_ID_KEY, company.getId())
                .claim(PAGE_ID_KEY, company.getPageId())
                .claim(LOCATION_CODE_KEY, company.getLocationCode());
                break;
            case USER:
                TokenUserDetails user = (TokenUserDetails)authentication.getPrincipal();
                jwtBuilder.claim(AUTHORITIES_KEY, authorities)
                    .claim(DISPLAY_NAME_KEY, user.getDisplayName())
                    .claim(USER_ID_KEY, user.getId())
                    .claim(LOCATION_CODE_KEY, user.getLocationCode());
                break;
            default:
                break;
        }

        return jwtBuilder
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact();
    }

    /**
     * Parse claims.
     *
     * @param token the token
     * @return the claims
     */
    public Claims parse(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();
        return claims;
    }

    /**
     * Gets authentication.
     *
     * @param token the token
     * @return the authentication
     */
    public Authentication getAuthentication(String token) {
        Claims claims = parse(token);
        return obtainAuthentication(token, claims);
    }

    /**
     * Gets authentication.
     *
     * @param token the token
     * @return the authentication
     */
    public Authentication getAuthenticationNoThrows(String token) {
        Claims claims;
        try {
            claims = parse(token);
        }
        catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return obtainAuthentication(token, claims);
    }

    /**
     * Obtain authentication authentication.
     *
     * @param token  the token
     * @param claims the claims
     * @return the authentication
     */
    protected Authentication obtainAuthentication(String token, Claims claims) {
        Set<TokenUserAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(TokenUserAuthority::new)
                        .collect(Collectors.toSet());

        TokenUserDetails principal = createTokenUserDetails(authorities);
        setIds(principal, claims);
        principal.setUsername(claims.getSubject());
        principal.setAuthorities(authorities);
        principal.setDisplayName((String) claims.get(DISPLAY_NAME_KEY));
        principal.setLocationCode((String) claims.get(LOCATION_CODE_KEY));

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * Create TokenUserDetails
     *
     * @param authorities the authorities
     * @return <pre>  * TokenStoreDetails if authorities contains STORE,  * TokenPageDetails if authorities contains COMPANY  * TokenUserDetails if authorities contains USER </pre>
     */
    protected TokenUserDetails createTokenUserDetails(Collection<TokenUserAuthority> authorities) {
        TokenUserDetails principal;
        if (authorities.contains(TokenUserAuthority.STORE)) {
            principal = new TokenStoreDetails();
        }
        else if (authorities.contains(TokenUserAuthority.COMPANY)) {
            principal = new TokenPageDetails();
        }
        else {
            principal = new TokenUserDetails();
        }
        return principal;
    }

    /**
     * Set user ID and store ID or company ID.
     *
     * @param principal the principal
     * @param claims    the claims
     */
    protected void setIds(TokenUserDetails principal, Claims claims) {
        // User ID
        setUserId(principal, claims);
        if (principal instanceof TokenStoreDetails) {
            // Store ID
            setStoreId((TokenStoreDetails) principal, claims);
        }
        else if (principal instanceof TokenPageDetails) {
            // Company ID
            setCompanyId((TokenPageDetails) principal, claims);
        }
    }

    /**
     * Set user ID and store ID or company ID.
     *
     * @param principal the principal
     * @param claims    the claims
     */
    protected void setUserId(TokenUserDetails principal, Claims claims) {
        Long userId = NumberUtils.toLong(String.valueOf(claims.get(USER_ID_KEY)), -1);
        if (userId == -1) {
            userId = null;
        }
        principal.setId(userId);
    }

    /**
     * Set user ID and store ID or company ID.
     *
     * @param principal the principal
     * @param claims    the claims
     */
    protected void setStoreId(TokenStoreDetails principal, Claims claims) {
        Long storeId = NumberUtils.toLong(String.valueOf(claims.get(STORE_ID_KEY)), -1);
        if (storeId == -1) {
            storeId = null;
        }
        principal.setStoreId(storeId);
    }

    /**
     * Set user ID and store ID or company ID.
     *
     * @param principal the principal
     * @param claims    the claims
     */
    protected void setCompanyId(TokenPageDetails principal, Claims claims) {
        Long companyId = NumberUtils.toLong(String.valueOf(claims.get(PAGE_ID_KEY)), -1);
        if (companyId == -1) {
            companyId = null;
        }
        principal.setPageId(companyId);
    }

    /**
     * Validate token boolean.
     *
     * @param authToken the auth token
     * @return the boolean
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        }
        catch (ExpiredJwtException e) {
            // Force throw 401
            log.warn("JWT expired: " + e.getMessage());
            return false;
        }
        catch (Exception e) {
            log.warn("Invalid JWT signature: " + e.getMessage());
            return false;
        }
    }
}
