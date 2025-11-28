package com.enesbayram.sm_post_manager.service;

import com.enesbayram.sm_core.base.dto.DtoPostLike;
import com.enesbayram.sm_core.base.dto.DtoPostLikeIU;
import com.enesbayram.sm_core.base.model.PostLike;
import com.enesbayram.sm_core.base.service.IBaseCrudService;
import com.enesbayram.sm_core.base.service.IBaseDbService;
import com.enesbayram.sm_post_manager.dto.PostLikeStatusResponse;

import java.util.List;

public interface IPostLikeService extends IBaseCrudService<PostLike> , IBaseDbService<PostLike> {

    PostLike savePostLike(DtoPostLikeIU dtoPostLikeIU);

    List<DtoPostLike> findPostLikeWithParams(String postId , String userDefId);

    Integer getPostLikeCount(String postId);

    Boolean deletePostLike(String postLikeId);

    Boolean switchPostLike (DtoPostLikeIU dtoPostLikeIU);

    PostLikeStatusResponse findPostLikeStatus(String postId, String userDefId);
}
