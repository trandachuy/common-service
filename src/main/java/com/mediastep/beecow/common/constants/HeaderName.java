package com.mediastep.beecow.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum HeaderName {
    TOTAL_COUNT("X-Total-Count"), TOTAL_REVENUE("X-Total-Revenue");
    private final String name;
}
