package com.enesbayram.sm_security_manager.service.impl;

import com.enesbayram.sm_core.base.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTService {

    public static final String SECRET_KEY = "JfVoLB61pmyKbWswGsuxD6TMnPFZzsoOACmSd1mA5hM=";


    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(DateUtils.convertLocalDateTimeToDate(LocalDateTime.now().plusHours(2)))
                .signWith(getKey() , SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token){
        Date expiredTime = exportToken(token, Claims::getExpiration);
        return expiredTime.after(new Date());
    }


    public <T> T exportToken(String token , Function<Claims , T> claimsFunc){
        Claims claims = getClaims(token);
        return claimsFunc.apply(claims);
    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
    }


    public Key getKey() {
        byte[] byteKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(byteKey);
    }
}
