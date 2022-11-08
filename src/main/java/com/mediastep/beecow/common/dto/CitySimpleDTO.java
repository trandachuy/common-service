package com.mediastep.beecow.common.dto;

/**
 * Copyright 2017 (C) Mediastep Software Inc.
 * <p>
 * Created on : 5/9/17
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 * @deprecated since sprint 35. Move to catalog-shared
 */
@Deprecated
public class CitySimpleDTO implements Comparable<CitySimpleDTO> {
    
    /** The code. */
    private String code;
    
    /** The in country. */
    private String inCountry;
    
    /** The out country. */
    private String outCountry;

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code.
     *
     * @param code the new code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the in country.
     *
     * @return the in country
     */
    public String getInCountry() {
        return inCountry;
    }

    /**
     * Sets the in country.
     *
     * @param inCountry the new in country
     */
    public void setInCountry(String inCountry) {
        this.inCountry = inCountry;
    }

    /**
     * Gets the out country.
     *
     * @return the out country
     */
    public String getOutCountry() {
        return outCountry;
    }

    /**
     * Sets the out country.
     *
     * @param outCountry the new out country
     */
    public void setOutCountry(String outCountry) {
        this.outCountry = outCountry;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(CitySimpleDTO o) {
        return this.getOutCountry().compareTo(o.getOutCountry());
    }
}
