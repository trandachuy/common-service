/*
 * *****************************************************************************
 *  Copyright 2017 (C) Mediastep Software Inc.
 *
 *  Created on : 14/1/2022
 *  Author: Dai Mai (dai.mai@mediastep.com)
 * *****************************************************************************
 */

package com.mediastep.beecow.common.rest;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/open-docs")
public class OpenAPIResource {

    @SneakyThrows
    @GetMapping
    public void forwardToSwagger(HttpServletRequest request, HttpServletResponse response) {
        request.getServletContext().getRequestDispatcher("/v2/api-docs?group=external").forward(request, response);
    }
}
