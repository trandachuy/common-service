/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common;

import com.mediastep.beecow.common.util.PhoneDtoUtil;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.mediastep.beecow.common"})
@PropertySource("classpath:application.properties")
public class BeecowCommonApplication {

    public static void main(String[] args) {
        String phoneCode = PhoneDtoUtil.getPredefinedPhoneCode("XxX", String::toLowerCase);
        System.out.println(phoneCode);
    }
}
