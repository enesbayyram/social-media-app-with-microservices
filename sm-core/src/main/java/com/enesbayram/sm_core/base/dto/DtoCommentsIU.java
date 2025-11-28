package com.enesbayram.sm_core.base.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCommentsIU extends DtoCrud{

    @NotBlank
    private String content;

    @NotBlank
    private String postId;

    @NotBlank
    private String userDefId;

    private String parentCommentId;

}
