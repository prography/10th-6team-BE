package com.prography.zone_2_be

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
@SpringBootApplication
class Zone2BeApplication

fun main(args: Array<String>) {
	runApplication<Zone2BeApplication>(*args)
}
