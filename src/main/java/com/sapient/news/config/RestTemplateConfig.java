package com.sapient.news.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
@Configuration
public class RestTemplateConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(requestFactory());
    }

    private SimpleClientHttpRequestFactory requestFactory() {
        return new SimpleClientHttpRequestFactory();
    }
}
