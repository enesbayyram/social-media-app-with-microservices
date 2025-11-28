package com.enesbayram.sm_core.base.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPost extends DtoBase {

    private String picture;

    private String content;

    private DtoUserDef userDef;

}
