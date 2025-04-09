package com.ua.yushchenko.it.provider;

import com.ua.yushchenko.configuration.ShowScoutObjectMapper;
import org.springframework.boot.test.web.client.TestRestTemplate;

/**
 * Base class for service client provider
 *
 * @author romanyushchenko
 * @version 0.1
 */
public abstract class BaseServiceClientProvider {

    protected final TestRestTemplate restTemplate;
    protected final ShowScoutObjectMapper objectMapper;
    protected final int port;

    protected BaseServiceClientProvider(final TestRestTemplate restTemplate,
                                        final ShowScoutObjectMapper objectMapper,
                                        final int port) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.port = port;
    }

    /**
     * Get full URL
     *
     * @param path part of URL
     * @return full URL
     */
    protected String url(final String path) {
        return "http://localhost:" + port + path;
    }
}
