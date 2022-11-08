package com.mediastep.beecow.common.config;

import com.mediastep.beecow.common.aop.annotation.PublishedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/01/2022
 * Author: Quy Luong (quy.luong@mediastep.com)
 *******************************************************************************/
@Configuration
public class ExternalSwaggerConfig {
    public static final String OPEN_API_GROUP_NAME = "external";
    
    @Bean
    public Docket swaggerSetting() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(
                        input -> input.isAnnotatedWith(PublishedOpenApi.class)
                                ||
                                Optional.ofNullable(input.declaringClass())
                                        .map(input1 -> input1.isAnnotationPresent(PublishedOpenApi.class))
                                        .orElse(false)
                )
                .build()
                .securitySchemes(Arrays.asList(this.basicScheme(), this.apiKeyScheme()))
                .securityContexts(Collections.singletonList(this.securityContext()))
                .groupName(OPEN_API_GROUP_NAME);
    }

    private SecurityScheme basicScheme() {
        return new BasicAuth("basic");
    }

    private SecurityScheme apiKeyScheme() {
        return new ApiKey("Bearer token", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
