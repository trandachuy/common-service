/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 5/2/2020
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.security;

import java.util.Collection;

/**
 * The interface Token gosell store details.
 */
public interface ITokenGosellStoreDetails extends ITokenStoreDetails {
    /**
     * Gets gosell features.
     *
     * @return the gosell features
     */
    Collection<String> getGosellFeatures();

    /**
     * Sets gosell features.
     *
     * @param gosellFeatures the gosell features
     */
    void setGosellFeatures(Collection<String> gosellFeatures);

    /**
     * Gets next expiry package time.
     *
     * @return the timestamp
     */
    Long getNextExpiryPackageTime();

    /**
     * Sets next expiry package time.
     *
     * @param nextExpiryPackageTime the next expiry package time
     */
    void setNextExpiryPackageTime(Long nextExpiryPackageTime);
}
