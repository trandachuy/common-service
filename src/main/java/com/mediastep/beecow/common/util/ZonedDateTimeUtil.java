/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 01/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *  
 *******************************************************************************/
package com.mediastep.beecow.common.util;

import java.time.*;

/**
 * Util to convert image URL from String to ImageDTO
 * @author huyen
 */
public class ZonedDateTimeUtil {

    public static LocalDate toLocalDate(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime.toLocalDate();
    }

    public static ZonedDateTime toZonedDateTime(LocalDate localDate) {
        return toZonedDateTime(localDate, null);
    }

    public static ZonedDateTime toZonedDateTime(LocalDate localDate, ZoneId zoneId) {
        if (localDate == null) {
            return null;
        }
        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }
        return localDate.atStartOfDay(zoneId);
    }

    public static LocalDateTime toLocalDateTime(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime.toLocalDateTime();
    }

    public static ZonedDateTime toZonedDateTime(LocalDateTime localDateTime) {
        return toZonedDateTime(localDateTime, null);
    }

    public static ZonedDateTime toZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        if (localDateTime == null) {
            return null;
        }
        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }
        return ZonedDateTime.of(localDateTime, zoneId);
    }

    public static String toString(ZoneId timeZone) {
        if (timeZone == null) {
            return null;
        }
        return timeZone.getId();
    }

    public static ZoneId toZoneId(String timeZone) {
        if (timeZone == null) {
            return null;
        }
        return ZoneId.of(timeZone);
    }

    public static String getZoneAsString(ZonedDateTime time) {
        if (time == null) {
            return null;
        }
        return toString(time.getZone());
    }
}
