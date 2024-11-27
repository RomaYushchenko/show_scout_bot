package com.ua.yushchenko.common.client.config;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpResponseInterceptor;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.apache.hc.core5.http.protocol.HttpContext;

/**
 * Class that represent configuration for Feign
 *
 * @author romanyushchenko
 * @version 0.1
 */
@Slf4j
public class FeignRequestLogger implements HttpRequestInterceptor, HttpResponseInterceptor {

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(final HttpRequest httpRequest, final EntityDetails entityDetails,
                        final HttpContext httpContext) throws HttpException, IOException {
        log.debug("Sending {} request. URL: {}", httpRequest.getMethod(), getUrl(httpRequest));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void process(final HttpResponse httpResponse, final EntityDetails entityDetails,
                        final HttpContext httpContext) throws HttpException, IOException {
        final var httpRequest = (BasicClassicHttpRequest) httpContext.getAttribute("http.request");
        final var methodType = httpRequest.getMethod();

        log.debug("Response {} {} CodeStatus:{}", methodType, getUrl(httpRequest), httpResponse.getCode());
    }

    private String getUrl(final HttpRequest httpRequest) {
        return httpRequest.getScheme() + "://" + httpRequest.getAuthority().toString() + httpRequest.getPath();
    }
}
