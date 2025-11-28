package com.enesbayram.sm_core.base.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoPostLike extends  DtoBase{

    private DtoPost post;

    private DtoUserDef userDef;
}

