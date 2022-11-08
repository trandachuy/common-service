/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String GUEST = "ROLE_GUEST";
    public static final String BEECOW = "ROLE_BEECOW";
    public static final String STORE = "ROLE_STORE";
    public static final String COMPANY = "ROLE_COMPANY";
    public static final String PARTNER = "ROLE_PARTNER";
    public static final String EDITOR = "ROLE_EDITOR";
    public static final String ACCOUNTANT = "ROLE_ACCOUNTANT";
    public static final String GUEST_CHECKOUT = "ROLE_GUEST_CHECKOUT";
    public static final String GOSELL_STAFF = "ROLE_GOSELL_STAFF";
    public static final String MARKETING = "ROLE_MARKETING";
    public static final String CUSTOMER_SERVICE = "ROLE_CUSTOMER_SERVICE";
    public static final String PARTNER_RESELLER = PARTNER + "_RESELLER";

    private AuthoritiesConstants() {
    }
}
