package com.mediastep.beecow.common.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/management/heartbeat")
public class HeartBeatResource {

    @GetMapping
    public String ping() {
        return "pong";
    }
}
