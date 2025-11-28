package com.enesbayram.sm_comment_manager.controller;

import com.enesbayram.sm_comment_manager.dto.DeleteCommentRequest;
import com.enesbayram.sm_core.base.dto.DtoComments;
import com.enesbayram.sm_core.base.dto.DtoCommentsIU;
import com.enesbayram.sm_core.base.model.base.RootEntity;

import java.util.List;

public interface IRestCommentsController {

    RootEntity<DtoComments> saveComment(DtoCommentsIU dtoCommentsIU);

    void saveCommentWithWS(DtoCommentsIU dtoCommentsIU);

    RootEntity<List<DtoComments>> findCommentsByPostId(String postId);

    RootEntity<Boolean> deleteCommentRecursive(String commentId);

    RootEntity<Boolean> deleteCommentWithWS(DeleteCommentRequest request);

}
