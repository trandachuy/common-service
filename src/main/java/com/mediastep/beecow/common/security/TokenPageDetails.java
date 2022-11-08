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
public class TokenPageDetails extends TokenUserDetails implements ITokenPageDetails {

    private static final long serialVersionUID = 1L;

    private Long pageId;

    public TokenPageDetails() {
    }

    public TokenPageDetails(TokenUserDetails tokenUserDetails) {
        this.setId(tokenUserDetails.getId());
        this.setDisplayName(tokenUserDetails.getDisplayName());
        this.setActivated(tokenUserDetails.isActivated());
        this.setAuthorities(tokenUserDetails.getAuthorities());
        this.setPassword(tokenUserDetails.getPassword());
        this.setUsername(tokenUserDetails.getUsername());
    }

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pageId", pageId)
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
