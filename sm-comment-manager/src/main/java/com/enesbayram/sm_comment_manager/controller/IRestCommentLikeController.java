package com.enesbayram.sm_comment_manager.controller;

import com.enesbayram.sm_core.base.dto.DtoCommentLike;
import com.enesbayram.sm_core.base.dto.DtoCommentLikeIU;
import com.enesbayram.sm_core.base.model.base.RootEntity;

import java.util.List;

public interface IRestCommentLikeController {


    RootEntity<DtoCommentLike> saveCommentLike(DtoCommentLikeIU dtoCommentLikeIU);

    RootEntity<Boolean> deleteCommentLike(String commentLikeId);

    RootEntity<List<DtoCommentLike>> findCommentLikeWithParams(String postId, String userDefId, String commentId);

    RootEntity<Integer> getCommentLikeCount(String postId, String userDefId, String commentId);

}
