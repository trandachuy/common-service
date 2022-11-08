/*
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 16/4/2021
 * Author: Dai Mai <email: dai.mai@mediastep.com>
 */

package com.mediastep.beecow.common.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring")
public abstract class DatetimeMapper {

    @Named(value = "truncateToSecond")
    public ZonedDateTime truncateToSecond(ZonedDateTime fulltime) {
        if (fulltime == null) {
            return null;
        }

        return fulltime.truncatedTo(ChronoUnit.SECONDS);
    }
}
