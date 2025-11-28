package com.enesbayram.sm_core.base.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoUserDef extends DtoBase {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String profilePhoto;
}
