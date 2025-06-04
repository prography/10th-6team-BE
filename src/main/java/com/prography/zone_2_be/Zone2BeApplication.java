package com.prography.zone_2_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;


@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class Zone2BeApplication {
    public static void main(String[] args) {
        SpringApplication.run(Zone2BeApplication.class, args);
    }
}
