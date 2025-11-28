package com.enesbayram.sm_post_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeStatusResponse {

    private Boolean isUserPostLike;

    private Integer postLikeCount;
}
