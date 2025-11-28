package com.enesbayram.sm_core.base.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCommentLike extends  DtoBase{

    private DtoPost post;

    private DtoComments comment;

    private DtoUserDef userDef;
}
