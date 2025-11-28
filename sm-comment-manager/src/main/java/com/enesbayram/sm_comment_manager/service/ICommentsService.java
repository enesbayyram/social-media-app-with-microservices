package com.enesbayram.sm_comment_manager.service;

import com.enesbayram.sm_core.base.dto.DtoComments;
import com.enesbayram.sm_core.base.dto.DtoCommentsIU;
import com.enesbayram.sm_core.base.model.Comments;
import com.enesbayram.sm_core.base.service.IBaseCrudService;
import com.enesbayram.sm_core.base.service.IBaseDbService;

import java.util.List;

public interface ICommentsService extends IBaseCrudService<Comments> , IBaseDbService<Comments> {

     Comments saveComment(DtoCommentsIU dtoCommentsIU);

     List<DtoComments> findCommentsByPostId(String postId);

     boolean deleteCommentRecursive(String commentId);
}
