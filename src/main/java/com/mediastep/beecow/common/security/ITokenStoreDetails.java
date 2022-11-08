/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 19/11/2019
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.security;

/**
 * The interface Token store details.
 */
public interface ITokenStoreDetails extends ITokenUserDetails {

    /**
     * Gets store id.
     *
     * @return the store id
     */
    Long getStoreId();
}
