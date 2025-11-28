package com.enesbayram.sm_post_manager.service.impl;

import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.dto.DtoPostLike;
import com.enesbayram.sm_core.base.dto.DtoPostLikeIU;
import com.enesbayram.sm_core.base.enums.MessageType;
import com.enesbayram.sm_core.base.exception.BaseException;
import com.enesbayram.sm_core.base.exception.ErrorMessage;
import com.enesbayram.sm_core.base.model.Post;
import com.enesbayram.sm_core.base.model.PostLike;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.service.impl.BaseDbServiceImpl;
import com.enesbayram.sm_post_manager.dto.PostLikeStatusResponse;
import com.enesbayram.sm_post_manager.repository.PostLikeRepository;
import com.enesbayram.sm_post_manager.service.IPostLikeService;
import com.enesbayram.sm_post_manager.service.IPostService;
import com.enesbayram.sm_post_manager.service.IUserDefService;
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
public class PostLikeServiceImpl extends BaseDbServiceImpl<PostLikeRepository, PostLike> implements IPostLikeService {

    private final IPostService postService;

    private final IUserDefService userDefService;

    @Override
    public Class<? extends DtoBase> getDTOClassForService() {
        return DtoPostLike.class;
    }

    @Override
    public PostLike savePostLike(DtoPostLikeIU dtoPostLikeIU) {

        Post post = postService.findById(dtoPostLikeIU.getPostId());
        if (post == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "post id : " + dtoPostLikeIU.getPostId()));
        }
        UserDef userDef = userDefService.findById(dtoPostLikeIU.getUserDefId());
        if (userDef == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "userDef id : " + dtoPostLikeIU.getUserDefId()));
        }

        PostLike postLike = toDaoForInsert(dtoPostLikeIU);
        postLike.setPost(post);
        postLike.setUserDef(userDef);
        return save(postLike);
    }

    @Override
    public List<DtoPostLike> findPostLikeWithParams(String postId, String userDefId) {
        Optional<List<PostLike>> optional = dao.findPostLikeWithParams(postId, userDefId);
        if (optional.isPresent() && !optional.get().isEmpty()) {
            return toDTOList(optional.get());
        }
        return Collections.emptyList();
    }

    @Override
    public Integer getPostLikeCount(String postId) {
        Optional<List<PostLike>> optional = dao.findPostLikeWithParams(postId, null);
        if (optional.isPresent() && !optional.get().isEmpty()) {
            return optional.get().size();
        }
        return 0;
    }

    @Override
    public Boolean deletePostLike(String postLikeId) {
        Optional<PostLike> optional = dao.findById(postLikeId);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, " postLikeId : " + postLikeId));
        }
        delete(optional.get());
        return true;
    }

    @Override
    public Boolean switchPostLike(DtoPostLikeIU dtoPostLikeIU) {
        Optional<PostLike> optional = dao.findPostLikeByPostIdAndUserDefId(dtoPostLikeIU.getPostId(), dtoPostLikeIU.getUserDefId());
        if (optional.isPresent()) {
            delete(optional.get());
        } else {
            savePostLike(new DtoPostLikeIU(dtoPostLikeIU.getPostId(), dtoPostLikeIU.getUserDefId()));
        }
        return true;
    }

    @Override
    public PostLikeStatusResponse findPostLikeStatus(String postId, String userDefId) {
        PostLikeStatusResponse response = new PostLikeStatusResponse();
        response.setPostLikeCount(getPostLikeCount(postId));
        response.setIsUserPostLike(isUserPostLike(postId, userDefId));

        return response;
    }

    private boolean isUserPostLike(String postId, String userDefId) {
        Optional<PostLike> optional = dao.findPostLikeByPostIdAndUserDefId(postId, userDefId);
        if (optional.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public <D extends DtoBase> List<D> toDTOList(List<PostLike> list) {
        List<DtoPostLike> dtoPostLikeList = new ArrayList<>();
        for (PostLike postLike : list) {
            dtoPostLikeList.add(toDTO(postLike));
        }
        return (List<D>) dtoPostLikeList;
    }

    @Override
    public <D extends DtoBase> D toDTO(PostLike entity) {
        DtoPostLike dtoPostLike = super.toDTO(entity);
        if (entity.getPost() != null) {
            dtoPostLike.setPost(postService.toDTO(entity.getPost()));
        }
        if (entity.getUserDef() != null) {
            dtoPostLike.setUserDef(userDefService.toDTO(entity.getUserDef()));
        }
        return (D) dtoPostLike;
    }
}
