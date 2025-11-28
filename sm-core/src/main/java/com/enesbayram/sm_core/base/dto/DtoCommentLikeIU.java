package com.enesbayram.sm_core.base.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCommentLikeIU extends DtoCrud{

    @NotBlank
    private String postId;

    @NotBlank
    private String commentId;

    @NotBlank
    private String userDefId;
}
