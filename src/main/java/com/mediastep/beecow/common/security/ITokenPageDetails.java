/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 19/11/2019
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.security;

/**
 * The interface Token page details.
 */
public interface ITokenPageDetails extends ITokenUserDetails {

    /**
     * Gets page id.
     *
     * @return the page id
     */
    Long getPageId();
}
