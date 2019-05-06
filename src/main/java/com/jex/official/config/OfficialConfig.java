package com.jex.official.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfficialConfig {
    @Value("${debug: false}")
    public boolean DEBUG;

    @Value("${web.host: \"\"}")
    public String WEB_HOST;


}
