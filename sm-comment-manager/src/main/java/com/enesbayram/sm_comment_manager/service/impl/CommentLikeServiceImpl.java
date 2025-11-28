package com.enesbayram.sm_comment_manager.service.impl;

import com.enesbayram.sm_comment_manager.repository.CommentLikeRepository;
import com.enesbayram.sm_comment_manager.service.ICommentLikeService;
import com.enesbayram.sm_comment_manager.service.ICommentsService;
import com.enesbayram.sm_comment_manager.service.IPostService;
import com.enesbayram.sm_comment_manager.service.IUserDefService;
import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.dto.DtoCommentLike;
import com.enesbayram.sm_core.base.dto.DtoCommentLikeIU;
import com.enesbayram.sm_core.base.enums.MessageType;
import com.enesbayram.sm_core.base.exception.BaseException;
import com.enesbayram.sm_core.base.exception.ErrorMessage;
import com.enesbayram.sm_core.base.model.CommentLike;
import com.enesbayram.sm_core.base.model.Comments;
import com.enesbayram.sm_core.base.model.Post;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.service.impl.BaseDbServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentLikeServiceImpl extends BaseDbServiceImpl<CommentLikeRepository, CommentLike> implements ICommentLikeService {

    private final IUserDefService userDefService;

    private final IPostService postService;

    private final ICommentsService commentsService;

    @Override
    public Class<? extends DtoBase> getDTOClassForService() {
        return DtoCommentLike.class;
    }

    @Override
    public CommentLike saveCommentLike(DtoCommentLikeIU dtoCommentLikeIU) {

        UserDef userDef = userDefService.findById(dtoCommentLikeIU.getUserDefId());
        if (userDef == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "userId : " + dtoCommentLikeIU.getUserDefId()));
        }

        Post post = postService.findById(dtoCommentLikeIU.getPostId());
        if (post == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "postId : " + dtoCommentLikeIU.getPostId()));
        }

        Comments comment = commentsService.findById(dtoCommentLikeIU.getCommentId());
        if (comment == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "commentd : " + dtoCommentLikeIU.getCommentId()));
        }

        CommentLike commentLike = toDaoForInsert(dtoCommentLikeIU);
        commentLike.setUserDef(userDef);
        commentLike.setPost(post);
        commentLike.setComment(comment);

        return save(commentLike);
    }

    @Override
    public Boolean deleteCommentLike(String commentLikeId) {
        Optional<CommentLike> optional = dao.findById(commentLikeId);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, commentLikeId));
        }
        delete(optional.get());
        return true;
    }

    @Override
    public List<DtoCommentLike> findCommentLikeWithParams(String postId, String userDefId, String commentId) {
        List<CommentLike> commentLikeList = dao.findCommentLikeWithParams(postId, userDefId, commentId);
        if (!commentLikeList.isEmpty()) {
            return toDTOList(commentLikeList);
        }
        return Collections.emptyList();
    }

    @Override
    public Integer getCommentLikeCount(String postId, String userDefId, String commentId) {
        List<CommentLike> commentLikeList = dao.findCommentLikeWithParams(postId, userDefId, commentId);
        return commentLikeList.size();
    }

    @Override
    public <D extends DtoBase> D toDTO(CommentLike entity) {
        DtoCommentLike dtoCommentLike = super.toDTO(entity);
        if (entity.getUserDef() != null) {
            dtoCommentLike.setUserDef(userDefService.toDTO(entity.getUserDef()));
        }
        if (entity.getPost() != null) {
            dtoCommentLike.setPost(postService.toDTO(entity.getPost()));
        }
        if (entity.getComment() != null) {
            dtoCommentLike.setComment(commentsService.toDTO(entity.getComment()));
        }
        return (D) dtoCommentLike;
    }

    @Override
    public <D extends DtoBase> List<D> toDTOList(List<CommentLike> list) {
        List<DtoCommentLike> dtoCommentLikeList = new ArrayList<>();
        for (CommentLike commentLike : list) {
            dtoCommentLikeList.add(toDTO(commentLike));
        }
        return (List<D>) dtoCommentLikeList;
    }
}
