package com.enesbayram.sm_comment_manager.service.impl;

import com.enesbayram.sm_comment_manager.repository.CommentsRepository;
import com.enesbayram.sm_comment_manager.service.ICommentsService;
import com.enesbayram.sm_comment_manager.service.IPostService;
import com.enesbayram.sm_comment_manager.service.IUserDefService;
import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.dto.DtoComments;
import com.enesbayram.sm_core.base.dto.DtoCommentsIU;
import com.enesbayram.sm_core.base.dto.DtoUserDef;
import com.enesbayram.sm_core.base.enums.MessageType;
import com.enesbayram.sm_core.base.exception.BaseException;
import com.enesbayram.sm_core.base.exception.ErrorMessage;
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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentsServiceImpl extends BaseDbServiceImpl<CommentsRepository, Comments> implements ICommentsService {

    private final IPostService postService;

    private final IUserDefService userDefService;

    @Override
    public Class<? extends DtoBase> getDTOClassForService() {
        return DtoComments.class;
    }

    @Override
    public Comments saveComment(DtoCommentsIU dtoCommentsIU) {
        Post post = postService.findById(dtoCommentsIU.getPostId());
        if (post == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCommentsIU.getPostId()));
        }

        UserDef userDef = userDefService.findById(dtoCommentsIU.getUserDefId());
        if (userDef == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCommentsIU.getUserDefId()));
        }

        Comments comment = toDaoForInsert(dtoCommentsIU);
        comment.setPost(post);
        comment.setUserDef(userDef);

        if (dtoCommentsIU.getParentCommentId() != null) {
            Comments parentComment = findById(dtoCommentsIU.getParentCommentId());
            if (parentComment == null) {
                throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCommentsIU.getParentCommentId()));
            }
            comment.setParentComment(parentComment);
        }
        return save(comment);
    }

    @Override
    public List<DtoComments> findCommentsByPostId(String postId) {

        List<DtoComments> dtoComments = new ArrayList<>();

        Optional<List<Comments>> optional = dao.findParentCommentsByPostId(postId);
        if (optional.isEmpty()) {
            return Collections.emptyList();
        }

        List<Comments> rootComments = optional.get();
        for (Comments rootComment : rootComments) {
            dtoComments.add(buildCommentsTree(rootComment));
        }
        return dtoComments;
    }

    private DtoComments buildCommentsTree(Comments rootComment) {
        DtoComments dtoComments = toDTO(rootComment);
        List<Comments> childComments = dao.findChildCommentsByParentId(rootComment.getId());
        for (Comments childComment : childComments) {
            dtoComments.getReplies().add(buildCommentsTree(childComment));
        }
        return dtoComments;
    }

    @Override
    public boolean deleteCommentRecursive(String commentId) {
        Optional<Comments> optional = dao.findById(commentId);
        if(optional.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST , commentId));
        }
        deleteComment(optional.get());
        return true;
    }

    private void deleteComment(Comments comment){
        List<Comments> childComments = dao.findChildCommentsByParentId(comment.getId());
        for(Comments child : childComments){
            deleteComment(child);
        }
        delete(comment);
    }

    @Override
    public <D extends DtoBase> D toDTO(Comments comment) {
        DtoComments dtoComment = super.toDTO(comment);
        if (comment.getPost() != null) {
            dtoComment.setPost(postService.toDTO(comment.getPost()));
        }
        if (comment.getUserDef() != null) {
            dtoComment.setUserDef(userDefService.toDTO(comment.getUserDef()));
        }

        if (comment.getParentComment() != null) {
            DtoComments dtoParentComment = new DtoComments();
            dtoParentComment.setId(comment.getParentComment().getId());
            dtoParentComment.setContent(comment.getParentComment().getContent());

            dtoComment.setParentComment(dtoParentComment);
        }
        return (D) dtoComment;
    }
}
