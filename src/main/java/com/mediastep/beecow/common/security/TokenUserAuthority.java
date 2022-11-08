/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.security;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class TokenUserAuthority implements Serializable, GrantedAuthority {

    public static final TokenUserAuthority ADMIN = new TokenUserAuthority(AuthoritiesConstants.ADMIN);

    public static final TokenUserAuthority USER = new TokenUserAuthority(AuthoritiesConstants.USER);

    public static final TokenUserAuthority ANONYMOUS = new TokenUserAuthority(AuthoritiesConstants.ANONYMOUS);

    public static final TokenUserAuthority GUEST = new TokenUserAuthority(AuthoritiesConstants.GUEST);

    public static final TokenUserAuthority BEECOW = new TokenUserAuthority(AuthoritiesConstants.BEECOW);

    public static final TokenUserAuthority STORE = new TokenUserAuthority(AuthoritiesConstants.STORE);
    
    public static final TokenUserAuthority COMPANY = new TokenUserAuthority(AuthoritiesConstants.COMPANY);

    public static final TokenUserAuthority PARTNER = new TokenUserAuthority(AuthoritiesConstants.PARTNER);

    public static final TokenUserAuthority PARTNER_RESELLER = new TokenUserAuthority(AuthoritiesConstants.PARTNER_RESELLER);

    public static final TokenUserAuthority EDITOR = new TokenUserAuthority(AuthoritiesConstants.EDITOR);

    public static final TokenUserAuthority ACCOUNTANT = new TokenUserAuthority(AuthoritiesConstants.ACCOUNTANT);

    public static final TokenUserAuthority GUEST_CHECKOUT = new TokenUserAuthority(AuthoritiesConstants.GUEST_CHECKOUT);

    public static final TokenUserAuthority GOSELL_STAFF = new TokenUserAuthority(AuthoritiesConstants.GOSELL_STAFF);

    private static final long serialVersionUID = 1L;

    private String name;

    public static TokenUserAuthority getAuthority(String name) {
        return new TokenUserAuthority(name);
    }

    public TokenUserAuthority() {
    }

    public TokenUserAuthority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o instanceof String || getClass() != o.getClass()) {
            return false;
        }

        String oname;
        if (o instanceof String) {
            oname = (String) o;
        }
        else {
            oname = ((TokenUserAuthority) o).getName();
        }

        if (name != null ? !name.equals(oname) : oname != null) {
            return false;
        }

        return true;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Authority{" +
            "name='" + name + '\'' +
            "}";
    }
}
