/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.security;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Deprecated
public class TokenStoreDetails extends TokenUserDetails implements ITokenStoreDetails {

    private static final long serialVersionUID = 1L;

    private Long storeId;

    public TokenStoreDetails() {
    }

    public TokenStoreDetails(TokenUserDetails tokenUserDetails) {
        this.setId(tokenUserDetails.getId());
        this.setDisplayName(tokenUserDetails.getDisplayName());
        this.setActivated(tokenUserDetails.isActivated());
        this.setAuthorities(tokenUserDetails.getAuthorities());
        this.setPassword(tokenUserDetails.getPassword());
        this.setUsername(tokenUserDetails.getUsername());
        this.setLocationCode(tokenUserDetails.getLocationCode());
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("storeId", storeId)
                .append("id", getId())
                .append("username", getUsername())
                .append("displayName", getDisplayName())
                .append("locationCode", getLocationCode())
                .append("activated", isActivated())
                .append("authorities", getAuthorities())
                .append("accountNonExpired", isAccountNonExpired())
                .append("accountNonLocked", isAccountNonLocked())
                .append("credentialsNonExpired", isCredentialsNonExpired())
                .append("enabled", isEnabled())
                .toString();
    }
}
