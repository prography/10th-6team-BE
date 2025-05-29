package com.prography.zone_2_be.global.health

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class HealthCheckController {

    @GetMapping("/health")
    fun health(): ResponseEntity<String> =
        ResponseEntity.ok("OK");
}