package com.enesbayram.sm_post_manager.repository;

import com.enesbayram.sm_core.base.model.Post;
import com.enesbayram.sm_core.base.repository.BaseDaoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends BaseDaoRepository<Post> {

    @Query(value = "from Post p WHERE p.userDef.id =:userId")
    Optional<List<Post>> findPostsByUserId(String userId);

    @Query(value = "from Post p ORDER BY p.createTime desc")
    Optional<List<Post>> findAllPost();
}
