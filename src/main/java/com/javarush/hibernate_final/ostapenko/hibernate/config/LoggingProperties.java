package com.javarush.hibernate_final.ostapenko.hibernate.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "custom.logging")
@Getter
@Setter
public class LoggingProperties {
    private boolean enabled = true;
    private String level ="INFO";
}
