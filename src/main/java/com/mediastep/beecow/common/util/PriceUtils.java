package com.mediastep.beecow.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import static com.mediastep.beecow.common.constants.StringConstants.*;
import static com.mediastep.beecow.common.constants.StringConstants.SPACE;

public class PriceUtils {
    public static String formatPrice(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return ZERO;
        }
        long number = bigDecimal.longValue();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        symbols.setDecimalSeparator(DOT.charAt(0));
        symbols.setGroupingSeparator(COMMA.charAt(0));
        NumberFormat formatter = new DecimalFormat("#,###.##", symbols);
        String formattedNumber = formatter.format(number);
        return formattedNumber.replace(".00", "");
    }

    public static String formatPriceNonVND(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return ZERO;
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH);
        symbols.setDecimalSeparator(DOT.charAt(0));
        symbols.setGroupingSeparator(COMMA.charAt(0));
        NumberFormat formatter = new DecimalFormat("#,###.00", symbols);
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        return formatter.format(bigDecimal);
    }

    public static String formatPrice(BigDecimal bigDecimal, String currency) {
        return (CURRENCY_SYMBOL_VN.equals(currency))
                ? formatPrice(bigDecimal) + SPACE + currency
                : currency + formatPriceNonVND(bigDecimal);
    }
}
