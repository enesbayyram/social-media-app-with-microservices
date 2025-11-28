package com.enesbayram.sm_comment_manager.repository;

import com.enesbayram.sm_core.base.model.CommentLike;
import com.enesbayram.sm_core.base.repository.BaseDaoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentLikeRepository extends BaseDaoRepository<CommentLike> {

    @Query(value = "from CommentLike cl WHERE 1=1 " +
            "AND (:postId is null or cl.post.id = :postId) " +
            "AND (:userDefId is null or cl.userDef.id = :userDefId " +
            "AND (:commentId is null or cl.comment.id = :commentId))")
    List<CommentLike> findCommentLikeWithParams(String postId , String userDefId , String commentId);
}
