/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger.Level;

/**
 * Config for UserResource and UserService
 */
@Configuration
public class BeecowFeignClientConfig {

    @Value("${beecow.feign.client.logger.level:NONE}")
    private Level feignLoggerLevel;

    /**
	 * @return the feignLoggerLevel
	 */
	public Level getFeignLoggerLevel() {
		return feignLoggerLevel;
	}

	/**
	 * @param feignLoggerLevel the feignLoggerLevel to set
	 */
	public void setFeignLoggerLevel(Level feignLoggerLevel) {
		this.feignLoggerLevel = feignLoggerLevel;
	}

	@Bean
    Level feignLoggerLevel() {
        return feignLoggerLevel;
    }
}
