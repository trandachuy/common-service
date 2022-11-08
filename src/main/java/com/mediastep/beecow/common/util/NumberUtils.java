package com.mediastep.beecow.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class NumberUtils {

    public static String numberWithoutScientificNotation(Number number) {
        if (Objects.isNull(number)) return "0.00";
        return new BigDecimal(number.toString()).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }

}
