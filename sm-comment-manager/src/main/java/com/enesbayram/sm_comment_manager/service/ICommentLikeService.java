package com.enesbayram.sm_comment_manager.service;

import com.enesbayram.sm_core.base.dto.DtoCommentLike;
import com.enesbayram.sm_core.base.dto.DtoCommentLikeIU;
import com.enesbayram.sm_core.base.model.CommentLike;
import com.enesbayram.sm_core.base.service.IBaseCrudService;
import com.enesbayram.sm_core.base.service.IBaseDbService;

import java.util.List;

public interface ICommentLikeService extends IBaseCrudService<CommentLike> , IBaseDbService<CommentLike> {

    CommentLike saveCommentLike(DtoCommentLikeIU dtoCommentLikeIU);

    Boolean deleteCommentLike(String commentLikeId);

    List<DtoCommentLike> findCommentLikeWithParams(String postId , String userDefId , String commentId);

    Integer getCommentLikeCount(String postId , String userDefId , String commentId);
}
