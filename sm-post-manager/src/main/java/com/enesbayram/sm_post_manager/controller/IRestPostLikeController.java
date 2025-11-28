package com.enesbayram.sm_post_manager.controller;

import com.enesbayram.sm_core.base.dto.DtoPostLike;
import com.enesbayram.sm_core.base.dto.DtoPostLikeIU;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_post_manager.dto.PostLikeStatusResponse;

import java.util.List;

public interface IRestPostLikeController {

    RootEntity<DtoPostLike> savePostLike(DtoPostLikeIU dtoPostLikeIU);

    RootEntity<List<DtoPostLike>> findPostLikeWithParams(String postId, String userDefId);

    RootEntity<Boolean> deletePostLike(String postLikeId);

    RootEntity<Boolean>  switchPostLike(DtoPostLikeIU dtoPostLikeIU);

    void switchPostLikeAndStatus(DtoPostLikeIU dtoPostLikeIU);

    RootEntity<PostLikeStatusResponse> findPostLikeStatus(String postId , String userDefId);

    void findPostLikeStatusWithWs(DtoPostLikeIU dtoPostLikeIU);


}
