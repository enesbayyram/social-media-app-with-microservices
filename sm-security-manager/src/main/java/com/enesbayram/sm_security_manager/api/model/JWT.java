package com.enesbayram.sm_security_manager.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWT {

    private String accessToken;

    private Date accessTokenExpireIn;

    private String refreshToken;

    private Date refreshTokenExpireIn;
}
