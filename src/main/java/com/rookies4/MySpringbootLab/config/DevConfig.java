// DevConfig.java
package com.rookies4.MySpringbootLab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    public MyEnvironment myEnvironment() {
        return MyEnvironment.builder().mode("DEV").build();
    }
}
