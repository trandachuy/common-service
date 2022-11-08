/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 19/11/2019
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * The interface Token user details.
 */
public interface ITokenUserDetails extends UserDetails, Serializable {

    /**
     * Gets id.
     *
     * @return the id
     */
    Long getId();

    /**
     * Get display name string.
     *
     * @return the string
     */
    String getDisplayName();

    /**
     * Gets location code.
     *
     * @return the location code
     */
    String getLocationCode();

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    Set<TokenUserAuthority> getAuthorities();
}
