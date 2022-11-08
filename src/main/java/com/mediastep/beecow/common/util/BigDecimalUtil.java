/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.util;

import java.math.BigDecimal;

import com.mediastep.beecow.common.config.Constants;

/**
 * Util to convert image URL from String to ImageDTO
 * @author huyen
 */
public class BigDecimalUtil {

	private BigDecimalUtil() {
	}

	public static BigDecimal refineAsCurrency(BigDecimal number) {
        if (number == null) {
            return null;
        }
        return number.setScale(Constants.CURRENCY_SCALE);
    }

	public static BigDecimal refineAsPersentage(BigDecimal number) {
        if (number == null) {
            return null;
        }
        return number.setScale(Constants.PERSENTAGE_SCALE);
    }
}
