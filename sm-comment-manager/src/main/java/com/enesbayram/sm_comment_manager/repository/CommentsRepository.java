package com.enesbayram.sm_comment_manager.repository;

import com.enesbayram.sm_core.base.model.Comments;
import com.enesbayram.sm_core.base.repository.BaseDaoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends BaseDaoRepository<Comments> {


    @Query(value = "from Comments c WHERE c.post.id=:postId and c.parentComment.id is null ORDER BY c.createTime desc")
    Optional<List<Comments>> findParentCommentsByPostId(String postId);

    @Query(value = "from Comments c WHERE c.parentComment.id = :parentCommentId ORDER BY c.createTime desc")
    List<Comments> findChildCommentsByParentId(String parentCommentId);


}
