package com.enesbayram.sm_post_manager.controller;


import com.enesbayram.sm_core.base.model.Post;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_core.base.dto.DtoPost;
import com.enesbayram.sm_post_manager.dto.PostRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IRestPostController {

    RootEntity<DtoPost> savePost(@RequestParam("postFile") MultipartFile postFile,
                              @RequestParam("content") String content,
                              @RequestParam("userDefId") String userDefId) throws IOException;

    void savePostWithWS(PostRequest request) throws IOException;

    public ResponseEntity<byte[]> getUserProfilePhoto(@PathVariable(value = "postId" , required = true) String postId);

    RootEntity<DtoPost> findByPostId(@PathVariable(value = "postId" , required = true) String postId);

    RootEntity<List<DtoPost>> findAllPost();

    RootEntity<List<DtoPost>> findPostsByUserId(@PathVariable(value = "userId" , required = true) String userId);

    RootEntity<Boolean> deletePostById(@PathVariable(value = "postId" , required = true) String postId);
}
