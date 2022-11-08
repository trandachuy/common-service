/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 19/11/2019
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.security;

import com.mediastep.beecow.common.config.BeecowConfig;
import com.mediastep.beecow.common.domain.enumeration.AuthorTypeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component("gosellTokenProvider")
@Primary
@Data
public class GosellTokenProvider extends TokenProvider implements ITokenProvider {

    public static final String STAFF_NAME = "staffName";

    public static final String STAFF_PERMISSION = "staffPermission";

    public static final String STORE_OWNER_ID = "storeOwnerId";

    public static final String GOSELL_FEATURES = "features";

    public static final String NEXT_PACKAGE_EXPIRY_TIME = "nextExpiryPackageTime";

    public static final String PARTNER_STATUS = "partnerStatus";

    public static final String PARTNER_CODE = "partnerCode";

    public static final String PARTNER_ID = "partnerId";

    public static final String PARTNER_TYPE = "partnerType";

    public static final String ALLOW_UPDATE_PRICE = "allowUpdatePrice";

    public static final String PARTNER_ENABLED = "partnerEnabled";

    public static final String HAS_PARTNER_PACKAGE = "hasPartnerPackage";

    private long gosellStaffTokenValidityInMilliseconds;

    private long gosellStaffTokenValidityInMillisecondsForRememberMe;

    private long gosellStoreTokenValidityInMilliseconds;

    private long gosellStoreTokenValidityInMillisecondsForRememberMe;

    private long temporaryTokenValidityInMilliseconds = 15 * 60 * 1000; // 15 minutes

    /**
     * Instantiates a new Token provider.
     *
     * @param beecowConfig the beecow config
     */
    public GosellTokenProvider(BeecowConfig beecowConfig) {
        super(beecowConfig);
        this.gosellStaffTokenValidityInMilliseconds = beecowConfig.getJwtGsStaffTokenValidity();
        this.gosellStaffTokenValidityInMillisecondsForRememberMe = beecowConfig.getJwtGsStaffTokenValidityForRememberMe();
        this.gosellStoreTokenValidityInMilliseconds = beecowConfig.getJwtGsStoreTokenValidity();
        this.gosellStoreTokenValidityInMillisecondsForRememberMe = beecowConfig.getJwtGsStoreTokenValidityForRememberMe();
    }

    /**
     * Create token string.
     *
     * @param authentication the authentication
     * @param rememberMe     the remember me
     * @return the string
     */
    @Override
    public String createToken(Authentication authentication, Boolean rememberMe, AuthorTypeEnum authorizeType) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        JwtBuilder builder = Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(SignatureAlgorithm.HS512, getSecretKey())
            ;
        TokenDetails details = (TokenDetails) authentication.getPrincipal();
        switch (authorizeType) {
            case STORE:
                this.addClaimsForStore(builder, details);
                this.setExpiration(builder, rememberMe);
                break;
            case COMPANY:
                this.addClaimsForPage(builder, details);
                this.setExpiration(builder, rememberMe);
                break;
            case GOSELL_STAFF:
                this.addClaimsForStaff(builder, details);
                this.setGoSellStaffExpiration(builder, rememberMe);
                break;
            case GOSELL_STORE:
                this.addClaimsForGoSellStore(builder, details);
                this.setGoSellStoreExpiration(builder, rememberMe);
                break;
            default:
                this.addClaimsForUser(builder, details);
                this.setExpiration(builder, rememberMe);
        }

        return builder.compact();
    }

    private void addClaimsForUser(JwtBuilder builder, TokenDetails details) {
        builder
            .claim(DISPLAY_NAME_KEY, details.getDisplayName())
            .claim(USER_ID_KEY, details.getId())
            .claim(LOCATION_CODE_KEY, details.getLocationCode())
        ;
    }

    private void addClaimsForStore(JwtBuilder builder, TokenDetails details) {
        this.addClaimsForUser(builder, details);
        builder
            .claim(STORE_ID_KEY, details.getStoreId())
        ;
    }

    private void addClaimsForPage(JwtBuilder builder, TokenDetails details) {
        this.addClaimsForUser(builder, details);
        builder
            .claim(PAGE_ID_KEY, details.getStoreId())
        ;
    }

    private void addClaimsForStaff(JwtBuilder builder, TokenDetails details) {
        this.addClaimsForGoSellStore(builder, details);
        builder
            .claim(STAFF_NAME, details.getStaffName())
            .claim(STAFF_PERMISSION, details.getStaffPermission())
            .claim(STORE_OWNER_ID, details.getStoreOwnerId())
        ;
    }

    private void addClaimsForGoSellStore(JwtBuilder builder, TokenDetails details) {
        this.addClaimsForStore(builder, details);
        builder
            .claim(GOSELL_FEATURES, details.getGosellFeatures())
            .claim(NEXT_PACKAGE_EXPIRY_TIME, details.getNextExpiryPackageTime())
        ;
        this.addClaimsForGoSellPartner(builder, details);
    }

    private void addClaimsForGoSellPartner(JwtBuilder builder, TokenDetails details) {
        builder
            .claim(PARTNER_ENABLED, details.isEnabledReseller())
            .claim(HAS_PARTNER_PACKAGE, details.isHasPackagePlan());
        if (details.getPartnerCode() != null) {
            builder
                .claim(PARTNER_CODE, details.getPartnerCode())
                .claim(PARTNER_ID, details.getPartnerId())
                .claim(PARTNER_STATUS, details.getPartnerStatus())
                .claim(PARTNER_TYPE, details.getPartnerType())
                .claim(ALLOW_UPDATE_PRICE, details.isAllowUpdatePrice())
            ;
        }
    }

    private void setExpiration(JwtBuilder builder, boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.getTokenValidityInMillisecondsForRememberMe());
        } else {
            validity = new Date(now + this.getTemporaryTokenValidityInMilliseconds());
        }
        builder.setExpiration(validity);
    }

    private void setGoSellStaffExpiration(JwtBuilder builder, boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.getGosellStaffTokenValidityInMillisecondsForRememberMe());
        } else {
            validity = new Date(now + this.getGosellStaffTokenValidityInMilliseconds());
        }
        builder.setExpiration(validity);
    }

    private void setGoSellStoreExpiration(JwtBuilder builder, boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.getGosellStoreTokenValidityInMillisecondsForRememberMe());
        } else {
            validity = new Date(now + this.getGosellStoreTokenValidityInMilliseconds());
        }
        builder.setExpiration(validity);
    }

    private void setTemporaryExpiration(JwtBuilder builder) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.temporaryTokenValidityInMilliseconds);
        builder.setExpiration(validity);
    }

    /**
     * Obtain authentication authentication.
     *
     * @param token  the token
     * @param claims the claims
     * @return the authentication
     */
    @Override
    protected Authentication obtainAuthentication(String token, Claims claims) {
        Set<TokenUserAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(TokenUserAuthority::new)
                        .collect(Collectors.toSet());

        TokenDetails.TokenDetailsBuilder principalBuilder = TokenDetails.builder();
        this.obtainPrincipalAuthorityDetails(principalBuilder, claims, authorities);
        this.obtainPrincipalUserDetails(principalBuilder, claims);
        this.obtainPrincipalStoreDetails(principalBuilder, claims);
        this.obtainPrincipalPageDetails(principalBuilder, claims);
        this.obtainPrincipalStaffDetails(principalBuilder, claims);
        this.obtainPrincipalGoSellStoreDetails(principalBuilder, claims);
        this.obtainPrincipalGoSellPartnerDetails(principalBuilder, claims);

        return new UsernamePasswordAuthenticationToken(principalBuilder.build(), token, authorities);
    }

    private void obtainPrincipalAuthorityDetails(TokenDetails.TokenDetailsBuilder builder, Claims claims, Set<TokenUserAuthority> authorities) {
        builder
            .username(claims.getSubject())
            .authorities(authorities)
        ;
    }

    private void obtainPrincipalUserDetails(TokenDetails.TokenDetailsBuilder builder, Claims claims) {
        builder
            .id(this.obtainPrincipalDetail(claims, USER_ID_KEY, Long.class))
            .locationCode(this.obtainPrincipalDetail(claims, LOCATION_CODE_KEY, String.class))
            .displayName(this.obtainPrincipalDetail(claims, DISPLAY_NAME_KEY, String.class))
        ;
    }

    private void obtainPrincipalStoreDetails(TokenDetails.TokenDetailsBuilder builder, Claims claims) {
        builder
            .storeId(this.obtainPrincipalDetail(claims, STORE_ID_KEY, Long.class))
        ;
    }

    private void obtainPrincipalPageDetails(TokenDetails.TokenDetailsBuilder builder, Claims claims) {
        builder
            .pageId(this.obtainPrincipalDetail(claims, PAGE_ID_KEY, Long.class))
        ;
    }

    private void obtainPrincipalStaffDetails(TokenDetails.TokenDetailsBuilder builder, Claims claims) {
        builder
            .staffName(this.obtainPrincipalDetail(claims, STAFF_NAME, String.class))
            .staffPermission(this.obtainPrincipalDetail(claims, STAFF_PERMISSION, String.class))
            .storeOwnerId(this.obtainPrincipalDetail(claims, STORE_OWNER_ID, Long.class))
        ;
    }

    private void obtainPrincipalGoSellStoreDetails(TokenDetails.TokenDetailsBuilder builder, Claims claims) {
        builder
            .gosellFeatures(this.obtainPrincipalDetail(claims, GOSELL_FEATURES, Collection.class))
            .nextExpiryPackageTime(this.obtainPrincipalDetail(claims, NEXT_PACKAGE_EXPIRY_TIME, Long.class))
        ;
    }

    private void obtainPrincipalGoSellPartnerDetails(TokenDetails.TokenDetailsBuilder builder, Claims claims) {
        Boolean partnerEnabled = this.obtainPrincipalDetail(claims, PARTNER_ENABLED, Boolean.class),
                hasPartnerPackage = this.obtainPrincipalDetail(claims, HAS_PARTNER_PACKAGE, Boolean.class);
        String partnerCode = this.obtainPrincipalDetail(claims, PARTNER_CODE, String.class);
        if (Boolean.TRUE.equals(partnerEnabled) &&Boolean.TRUE.equals(hasPartnerPackage) && partnerCode != null) {
            builder
                .enabledReseller(partnerEnabled)
                .hasPackagePlan(hasPartnerPackage)
                .partnerCode(partnerCode)
                .partnerId(this.obtainPrincipalDetail(claims, PARTNER_ID, Long.class))
                .partnerType(this.obtainPrincipalDetail(claims, PARTNER_TYPE, String.class))
                .partnerStatus(this.obtainPrincipalDetail(claims, PARTNER_STATUS, String.class))
                .allowUpdatePrice(this.obtainPrincipalDetail(claims, ALLOW_UPDATE_PRICE, Boolean.class));
        }
        else {
            builder
                .enabledReseller(false)
                .hasPackagePlan(false);
        }
    }

    private <T> T obtainPrincipalDetail(Claims claims, String field, Class<T> clazz) {
        return claims.get(field, clazz);
    }

    /**
     * Create temporary token string.
     *
     * @param authentication the authentication
     * @return the string
     */
    public String createTemporaryToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        JwtBuilder builder = Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(SignatureAlgorithm.HS512, getSecretKey())
            ;
        TokenDetails details = (TokenDetails) authentication.getPrincipal();
        this.addClaimsForUser(builder, details);
        this.setTemporaryExpiration(builder);
        return builder.compact();
    }
}
