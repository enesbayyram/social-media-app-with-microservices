package com.enesbayram.sm_post_manager.service.impl;

import com.enesbayram.sm_client.feign.UserManagerClient;
import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.enums.MessageType;
import com.enesbayram.sm_core.base.exception.BaseException;
import com.enesbayram.sm_core.base.exception.ErrorMessage;
import com.enesbayram.sm_core.base.model.Post;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_core.base.service.impl.BaseDbServiceImpl;
import com.enesbayram.sm_core.base.dto.DtoPost;
import com.enesbayram.sm_core.base.dto.DtoPostIU;
import com.enesbayram.sm_post_manager.dto.PostRequest;
import com.enesbayram.sm_post_manager.repository.PostRepository;
import com.enesbayram.sm_post_manager.service.IPostService;
import com.enesbayram.sm_post_manager.service.IUserDefService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl extends BaseDbServiceImpl<PostRepository, Post> implements IPostService {

    private final UserManagerClient userManagerClient;

    private final IUserDefService userDefService;

    @Value("${profiles.host.url}")
    private String url;


    @Override
    public Class<? extends DtoBase> getDTOClassForService() {
        return DtoPost.class;
    }

    @Override
    public DtoPost savePost(MultipartFile postFile, DtoPostIU dtoPostIU) throws IOException {

        try {
            RootEntity<UserDef> rootEntity = userManagerClient.findByUserId(dtoPostIU.getUserDefId());
            if (rootEntity.getData() == null) {
                throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoPostIU.getUserDefId()));
            }
            Post post = toDaoForInsert(dtoPostIU);
            post.setUserDef(rootEntity.getData());
            post.setPicture(postFile.getBytes());
            return toDTO(save(post));
        } catch (Exception e) {
            throw new RuntimeException("Post yüklenirken hata oluştu : " + e.getMessage());
        }
    }

    private Post createPostModel(PostRequest request, UserDef userDef) {
        Post post = new Post();
        post.setCreateTime(LocalDateTime.now());
        post.setCreateUser("x");
        post.setPicture(Base64.getDecoder().decode(request.getPostFile()));
        post.setContent(request.getContent());
        post.setUserDef(userDef);
        return post;
    }

    @Override
    public void savePostWithWS(PostRequest request) {
        try {
            UserDef userDef = userDefService.findById(request.getUserDefId());
            if (userDef== null) {
                throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, request.getUserDefId()));
            }
            save(createPostModel(request , userDef));
        } catch (Exception e) {
            throw new RuntimeException("Post yüklenirken hata oluştu : " + e.getMessage());
        }
    }

    @Override
    public Post findByPostId(String postId) {
        Optional<Post> optional = dao.findById(postId);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, postId));
        }
        return optional.get();
    }

    @Override
    public List<Post> findPostsByUserId(String userId) {
        Optional<List<Post>> optional = dao.findPostsByUserId(userId);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, userId));
        }
        return optional.get();
    }

    @Override
    public boolean deletePostById(String postId) {
        Post post = findByPostId(postId);
        delete(post);
        log.info(postId + " id li post başarıyla silinmiştir.");

        return true;
    }

    @Override
    public List<DtoPost> findAllPost() {
        Optional<List<Post>> optional = dao.findAllPost();
        if (optional.isPresent() && !optional.get().isEmpty()) {
            return toDTOList(optional.get());
        }
        return Collections.emptyList();
    }

    @Override
    public <D extends DtoBase> List<D> toDTOList(List<Post> list) {
        List<DtoPost> dtoPostList = new ArrayList<>();
        for (Post post : list) {
            dtoPostList.add(toDTO(post));
        }
        return (List<D>) dtoPostList;
    }

    @Override
    public <D extends DtoBase> D toDTO(Post entity) {
        DtoPost dtoPost = super.toDTO(entity);
        dtoPost.setPicture(url+"/sm-post-manager/post/image/"+entity.getId());

        if (entity.getUserDef() != null) {
            dtoPost.setUserDef(userDefService.toDTO(entity.getUserDef()));
        }
        return (D) dtoPost;
    }
}
