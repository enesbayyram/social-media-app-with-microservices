package com.enesbayram.sm_core.base.filter;

import com.enesbayram.sm_core.base.constant.GlobalConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isWhiteList(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String username = request.getHeader("X-USER");

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }



    private List<String> getWhiteList(){
        List<String> whiteList = new ArrayList<>();
        whiteList.add(GlobalConstant.AUTHENTICATE_WITH_CONTEXT_PATH);
        whiteList.add(GlobalConstant.REGISTER_WITH_CONTEXT_PATH);
        whiteList.add(GlobalConstant.REFRESH_TOKEN_WITH_CONTEXT_PATH);
        return whiteList;
    }

    private boolean isWhiteList(HttpServletRequest request){
        String requestPath = request.getRequestURI();
        if(requestPath.contains("ws") ||requestPath.contains("/sm-user-manager/user/image")
        || requestPath.contains("/sm-post-manager/post/image")){
            return true;
        }
        return getWhiteList().stream().anyMatch(r -> r.equals(requestPath));
    }
}
