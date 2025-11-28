package com.enesbayram.sm_post_manager.service;

import com.enesbayram.sm_core.base.dto.DtoPost;
import com.enesbayram.sm_core.base.dto.DtoPostIU;
import com.enesbayram.sm_core.base.model.Post;
import com.enesbayram.sm_core.base.service.IBaseCrudService;
import com.enesbayram.sm_core.base.service.IBaseDbService;
import com.enesbayram.sm_post_manager.dto.PostRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService extends IBaseCrudService<Post> , IBaseDbService<Post> {

     DtoPost savePost(MultipartFile multipartFile, DtoPostIU dtoPostIU) throws IOException;

     void savePostWithWS(PostRequest request);

     Post findByPostId(String postId);

     List<Post> findPostsByUserId(String userId);

     boolean deletePostById(String postId);

     List<DtoPost> findAllPost();
}

