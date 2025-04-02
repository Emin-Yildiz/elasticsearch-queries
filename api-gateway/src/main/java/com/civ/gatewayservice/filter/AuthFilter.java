package com.civ.gatewayservice.filter;

import com.civ.gatewayservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;


import java.util.List;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private final JwtUtil jwtUtil;
    private final RouteValidator routeValidator;

    public AuthFilter(JwtUtil jwtUtil, RouteValidator routeValidator) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.routeValidator = routeValidator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                // güvenlik doğrulaması gerektiren işlemler burada gerçekleşir.

                // Header'da token var mı yok mu kontrolü burada gerçekleşir.
                ServerHttpRequest request = exchange.getRequest();
                var requestHeader = request.getHeaders();
                if (!requestHeader.containsKey(HttpHeaders.AUTHORIZATION))
                    return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);

                // Header'dan jwt alma
                String jwt = jwtUtil.getJwtByAuthorizationHeader(requestHeader);
                var jwtBody = jwtUtil.isJwtValid(jwt);
                if (!jwtBody.getIsValid())
                    return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);

                exchange.getRequest().mutate()
                        .header("userName", jwtBody.getSubject())
                        .header("userId", jwtBody.getUserId())
                        .header("mail", jwtBody.getMail());
            }
            return chain.filter(exchange);
        };

    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }



    public static class Config {
        private List<String> roles;

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

    }
}
