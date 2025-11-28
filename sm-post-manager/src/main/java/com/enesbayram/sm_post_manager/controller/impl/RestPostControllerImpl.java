package com.enesbayram.sm_post_manager.controller.impl;

import com.enesbayram.sm_core.base.dto.DtoPost;
import com.enesbayram.sm_core.base.dto.DtoPostIU;
import com.enesbayram.sm_core.base.model.Post;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_core.base.restservice.base.RestBaseController;
import com.enesbayram.sm_post_manager.controller.IRestPostController;
import com.enesbayram.sm_post_manager.dto.PostRequest;
import com.enesbayram.sm_post_manager.service.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class RestPostControllerImpl extends RestBaseController implements IRestPostController {

    private final IPostService postService;

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public RootEntity<DtoPost> savePost(@RequestPart(value = "postFile") MultipartFile postFile,
                                        @RequestPart(value = "content") String content,
                                        @RequestPart(value = "userDefId") String userDefId) throws IOException {
       DtoPost dtoPost =  postService.savePost(postFile, new DtoPostIU(content, userDefId));
        messagingTemplate.convertAndSend("/topic/posts" , postService.findAllPost());
        return ok(dtoPost);
    }

    @MessageMapping("/save-with-ws")
    @Override
    public void savePostWithWS(@Payload PostRequest request) throws IOException {
        postService.savePostWithWS(request);
        messagingTemplate.convertAndSend("/topic/posts" , postService.findAllPost());
    }


    @GetMapping(path = "/image/{postId}" ,  produces = MediaType.IMAGE_JPEG_VALUE)
    @Override
    public ResponseEntity<byte[]> getUserProfilePhoto(@PathVariable(value = "postId" , required = true) String postId) {
        Post post = postService.findById(postId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(post.getPicture());
    }

    @GetMapping(path = "/list/{postId}")
    @Override
    public RootEntity<DtoPost> findByPostId(@PathVariable(value = "postId", required = true) String postId) {
        return ok(postService.toDTO(postService.findByPostId(postId)));
    }

    @GetMapping(path = "/list-all")
    @Override
    public RootEntity<List<DtoPost>> findAllPost() {
        return ok(postService.findAllPost());
    }

    @GetMapping(path = "/list/by-user/{userId}")
    @Override
    public RootEntity<List<DtoPost>> findPostsByUserId(@PathVariable(value = "userId", required = true) String userId) {
        return ok(postService.toDTOList(postService.findPostsByUserId(userId)));
    }

    @DeleteMapping(path = "/delete/{postId}")
    @Override
    public RootEntity<Boolean> deletePostById(@PathVariable(value = "postId", required = true) String postId) {
        return ok(postService.deletePostById(postId));
    }
}
