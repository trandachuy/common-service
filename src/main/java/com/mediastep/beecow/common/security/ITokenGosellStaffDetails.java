/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 19/11/2019
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.security;

/**
 * The interface Token gosell staff details.
 */
public interface ITokenGosellStaffDetails extends ITokenGosellStoreDetails {

    /**
     * Gets staff name.
     *
     * @return the staff name
     */
    String getStaffName();

    /**
     * Sets staff name.
     *
     * @param staffName the staff name
     */
    void setStaffName(String staffName);

    /**
     * Gets staff permission.
     *
     * @return the staff permission
     */
    String getStaffPermission();

    /**
     * Sets staff permission.
     *
     * @param staffPermission the staff permission
     */
    void setStaffPermission(String staffPermission);

    /**
     * Gets store owner id.
     *
     * @return the store owner id
     */
    Long getStoreOwnerId();

    /**
     * Sets store owner id.
     *
     * @param storeOwnerId the store owner id
     */
    void setStoreOwnerId(Long storeOwnerId);
}
