package com.enesbayram.sm_core.base.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DtoRefreshToken extends DtoBase {

    private DtoUserDef userDef;

    private String refreshToken;

    private LocalDateTime expireIn;
}
