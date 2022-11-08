/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 25/8/2021
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.security;

public interface ITokenGosellPartnerDetails extends ITokenGosellStoreDetails {

    String getPartnerStatus();

    void setPartnerStatus(String partnerStatus);

    String getPartnerCode();

    void setPartnerCode(String partnerCode);

    Long getPartnerId();

    void setPartnerId(Long partnerId);

    String getPartnerType();

    void setPartnerType(String partnerType);

    boolean isAllowUpdatePrice();

    void setAllowUpdatePrice(boolean allowUpdatePrice);

    boolean isEnabledReseller();

    void setEnabledReseller(boolean enabledReseller);

    boolean isHasPackagePlan();

    void setHasPackagePlan(boolean hasPackagePlan);
}
