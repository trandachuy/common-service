package com.mediastep.beecow.common.util;

import java.math.BigDecimal;

/**
 * Copyright 2017 (C) Mediastep Software Inc.
 * <p>
 * Created on : 2/2/17
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 */

public class RoundNumber {
    private static String[] denominations = {"", "K", "M", "B", "T"};

    public static String formatNumber(double number)
    {
        int denominationIndex = 0;

        // If number is greater than 1000, divide the number by 1000 and
        // increment the index for the denomination.
        if(number < 1000) return String.valueOf((int)number);

        while(number >= 1000)
        {
            denominationIndex++;
            number = number / 1000;
        }

        // To round it to 2 digits.
        BigDecimal bigDecimal = new BigDecimal(number);
        bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_EVEN);

        // Add the number with the denomination to get the final value.
        String formattedNumber = bigDecimal + denominations[denominationIndex];
        String[] parts = formattedNumber.split("\\.");
        if(parts[1].equalsIgnoreCase("0M") || parts[1].equalsIgnoreCase("0K")
                || parts[1].equalsIgnoreCase("0B") || parts[1].equalsIgnoreCase("0T")){
            formattedNumber = parts[0] + denominations[denominationIndex];
        }

        return formattedNumber;
    }
}
