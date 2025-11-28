package com.enesbayram.sm_core.base.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DtoComments extends  DtoBase{

    private String content;

    private DtoPost post;

    private DtoUserDef userDef;

    private DtoComments parentComment;

    private List<DtoComments> replies = new ArrayList<>();
}
