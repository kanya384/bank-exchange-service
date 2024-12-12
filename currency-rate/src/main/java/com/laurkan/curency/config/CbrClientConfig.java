package com.laurkan.curency.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cbr.client")
@Data
public class CbrClientConfig {
    private String url;
}
