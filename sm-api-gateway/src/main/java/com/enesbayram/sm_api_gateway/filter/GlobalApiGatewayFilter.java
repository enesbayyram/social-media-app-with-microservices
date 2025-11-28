package com.enesbayram.sm_api_gateway.filter;

import com.enesbayram.sm_api_gateway.model.UserDef;
import com.enesbayram.sm_api_gateway.service.IUserDefService;
import com.enesbayram.sm_api_gateway.service.JWTService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@Order(1)
public class GlobalApiGatewayFilter implements GlobalFilter {

    private final JWTService jwtService;

    private final IUserDefService userDefService;

    public static final String AUTHENTICATE = "/sm-security-manager/authenticate";
    public static final String REGISTER = "/sm-security-manager/register";
    public static final String REFRESH_TOKEN = "/sm-security-manager/refreshToken";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String requestPath = exchange.getRequest().getPath().value();
        if (isWhiteList(requestPath)) {
            return chain.filter(exchange);
        }
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorization == null) {
            return throwUnAuthorized(exchange);
        }
        String token = authorization.substring(7);

        try {
            if (!checkJWTToken(token)) {
                return throwUnAuthorized(exchange);
            }
        } catch (RuntimeException e) {
            return throwUnAuthorized(exchange);
        }

        ServerWebExchange mutateExchange = addUserHeaders(exchange, token);
        return chain.filter(mutateExchange);
    }


    private boolean isWhiteList(String requestPath) {
        if(requestPath.contains("ws") ||requestPath.contains("/sm-user-manager/user/image")
        ||requestPath.contains("/sm-post-manager/post/image")){
            return true;
        }
        return getWhiteList().stream().anyMatch(whiteEndpoint -> whiteEndpoint.equals(requestPath));
    }

    public ServerWebExchange addUserHeaders(ServerWebExchange exchange, String token) {
        String username = jwtService.exportToken(token, Claims::getSubject);
        return exchange.mutate().request(r -> r.header("X-USER", username)).build();
    }

    private List<String> getWhiteList() {
        List<String> whiteList = new ArrayList<>();
        whiteList.add(AUTHENTICATE);
        whiteList.add(REGISTER);
        whiteList.add(REFRESH_TOKEN);
        return whiteList;
    }

    private Mono<Void> throwUnAuthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }


    private boolean checkJWTToken(String token) {
        String username = jwtService.exportToken(token, Claims::getSubject);
        if (username == null) {
            return false;
        }
        UserDef userDef = userDefService.findByUsername(username);
        if (userDef == null) {
            return false;
        }
        boolean tokenValid = jwtService.isTokenValid(token);
        if (!tokenValid) {
            return false;
        }
        return true;
    }
}
