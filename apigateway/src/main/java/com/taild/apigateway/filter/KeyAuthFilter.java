package com.taild.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.Objects;


@Component
public class KeyAuthFilter extends AbstractGatewayFilterFactory<KeyAuthFilter.Config> {

    public KeyAuthFilter() {
        super(Config.class);
    }

    @Value("${api.key}")
    private String apiKey;

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey("apiKey")) {
                return handleException(exchange, "API Key is missing", HttpStatus.UNAUTHORIZED);
            }

            String apiKey = exchange.getRequest().getHeaders().getFirst("apiKey");

            if (!Objects.requireNonNull(apiKey).equals(this.apiKey)) {
                return handleException(exchange, "Invalid API Key", HttpStatus.FORBIDDEN);
            }

            ServerHttpRequest request = exchange.getRequest();

            return chain.filter(exchange.mutate().request(request).build());
        };

    }


    private Mono<Void> handleException(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorResponse = String.format(
                "{\"timestamp\": \"%s\", \"status\" : \"%d\" , \"error\" : \"%s\" , \"message\": \"%s\", \"path\": \"%s\"}",
                ZonedDateTime.now(), httpStatus.value(), httpStatus.getReasonPhrase(), message, exchange.getRequest().getURI().getPath());

        return response.writeWith(Mono.just(response.bufferFactory().wrap(errorResponse.getBytes())));
    }


    static class Config {

    }
}
