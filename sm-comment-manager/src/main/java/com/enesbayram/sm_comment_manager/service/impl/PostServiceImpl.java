package com.enesbayram.sm_comment_manager.service.impl;

import com.enesbayram.sm_comment_manager.repository.PostRepository;
import com.enesbayram.sm_comment_manager.service.IPostService;
import com.enesbayram.sm_core.base.dto.DtoBase;
import com.enesbayram.sm_core.base.dto.DtoPost;
import com.enesbayram.sm_core.base.model.Post;
import com.enesbayram.sm_core.base.service.impl.BaseDbServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl extends BaseDbServiceImpl<PostRepository , Post> implements IPostService {

    @Override
    public Class<? extends DtoBase> getDTOClassForService() {
        return DtoPost.class;
    }
}
