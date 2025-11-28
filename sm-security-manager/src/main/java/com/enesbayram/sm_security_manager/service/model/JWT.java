package com.enesbayram.sm_security_manager.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWT {

    private String accessToken;

    private LocalDateTime accessTokenExpired;

    private String refreshToken;

    private LocalDateTime refreshTokenExpired;
}
