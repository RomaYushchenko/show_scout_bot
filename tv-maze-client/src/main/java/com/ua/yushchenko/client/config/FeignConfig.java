package com.ua.yushchenko.client.config;

import feign.Client;
import feign.hc5.ApacheHttp5Client;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class that represent configuration for Feign
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Configuration
public class FeignConfig {

    @Bean
    public Client feignClient(final HttpClientConnectionManager connectionManger) {
        final var httpClient = HttpClientBuilder.create()
                                                .setConnectionManager(connectionManger)
                                                .disableContentCompression()
                                                .disableAutomaticRetries()
                                                .addRequestInterceptorFirst(new FeignRequestLogger())
                                                .addResponseInterceptorLast(new FeignRequestLogger())
                                                .build();

        return new ApacheHttp5Client(httpClient);
    }

}
