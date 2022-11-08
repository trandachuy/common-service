/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.util;

public class LocationCodeHelper {
	private static final String DELIMITER_CHARACTER = "-";

	/**
	 * Get country code from location code
	 * @param locationCode
	 * @return null if locationCode null
	 */
	public static String getCountryCode(String locationCode) {
	    if (locationCode == null) {
	        return null;
	    }
		String[] locationArray = locationCode.split(DELIMITER_CHARACTER);
		return locationArray[0];
	}
	
    /**
     * Get city/state/province code from location code
     * @param locationCode
     * @return null of locationCode null or does not content city/state/province code
     */
	public static String getCityCode(String locationCode) {
        if (locationCode == null) {
            return null;
        }
		String[] locationArray = locationCode.split(DELIMITER_CHARACTER);
		if (locationArray.length >= 2) {
		    return locationArray[1];
	    }
    	else {
    	    return null;
    	}
	}
}
