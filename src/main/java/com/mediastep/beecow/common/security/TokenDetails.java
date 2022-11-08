/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 19/11/2019
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.security;

import lombok.*;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Token details.
 */
@Data
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TokenDetails implements ITokenUserDetails, ITokenStoreDetails, ITokenPageDetails, ITokenGosellStaffDetails, ITokenGosellStoreDetails, ITokenGosellPartnerDetails, SocialUserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String displayName;

    private String locationCode;

    private boolean activated = false;

    private Set<TokenUserAuthority> authorities = new HashSet<>();

    private Long storeId;

    private Long pageId;

    private String staffName;

    private String staffPermission;

    private Long storeOwnerId;

    @Getter(AccessLevel.NONE)
    private Collection<String> gosellFeatures = new HashSet<>();

    private Long nextExpiryPackageTime;

    private String partnerStatus;

    private String partnerCode;

    private Long partnerId;

    private String partnerType;

    private boolean allowUpdatePrice;

    private boolean enabledReseller;

    private boolean hasPackagePlan;

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.isActivated();
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return this.isActivated();
    }

    @Override
    public Collection<String> getGosellFeatures() {
        return this.gosellFeatures == null ? Collections.emptyList() : this.gosellFeatures;
    }

    @Override
    public String getUserId() {
        return this.id.toString();
    }
}
