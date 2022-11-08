/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.util;

import org.springframework.stereotype.Component;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@Component
public class BitUtil {

    private BitUtil() {
    }

    public static boolean isSet(int flags, int mask) {
    	return (flags & mask) > 0;
    }
}
