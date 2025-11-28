package com.enesbayram.sm_core.base.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor  implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null && auth.getName()!=null){
            requestTemplate.header("X-USER" , auth.getName());
        }
    }
}
