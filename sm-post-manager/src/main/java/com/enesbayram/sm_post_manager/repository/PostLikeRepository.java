package com.enesbayram.sm_post_manager.repository;

import com.enesbayram.sm_core.base.model.PostLike;
import com.enesbayram.sm_core.base.repository.BaseDaoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends BaseDaoRepository<PostLike> {

    @Query(value = "from PostLike pl WHERE  1=1 " +
            "AND (:postId is null or pl.post.id = :postId) " +
            "AND (:userDefId is null or pl.userDef.id = :userDefId)")
    Optional<List<PostLike>> findPostLikeWithParams(String postId , String userDefId);


    @Query(value = "from PostLike pl WHERE pl.post.id = :postId and pl.userDef.id = :userDefId")
    Optional<PostLike> findPostLikeByPostIdAndUserDefId(String postId , String userDefId);
}
