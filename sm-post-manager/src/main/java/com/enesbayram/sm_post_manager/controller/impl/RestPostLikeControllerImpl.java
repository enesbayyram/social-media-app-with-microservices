package com.enesbayram.sm_post_manager.controller.impl;

import com.enesbayram.sm_core.base.dto.DtoPostLike;
import com.enesbayram.sm_core.base.dto.DtoPostLikeIU;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_core.base.restservice.base.RestBaseController;
import com.enesbayram.sm_post_manager.controller.IRestPostLikeController;
import com.enesbayram.sm_post_manager.dto.PostLikeStatusResponse;
import com.enesbayram.sm_post_manager.service.IPostLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post-like")
public class RestPostLikeControllerImpl extends RestBaseController implements IRestPostLikeController {

    private final IPostLikeService postLikeService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping(path = "/save")
    @Override
    public RootEntity<DtoPostLike> savePostLike(@RequestBody @Valid DtoPostLikeIU dtoPostLikeIU) {
        return ok(postLikeService.toDTO(postLikeService.savePostLike(dtoPostLikeIU)));
    }

    @GetMapping(path = "/list/with-params")
    @Override
    public RootEntity<List<DtoPostLike>> findPostLikeWithParams(@RequestParam(value = "postId", required = false) String postId,
                                                                @RequestParam(value = "userDefId", required = false) String userDefId) {
        return ok(postLikeService.findPostLikeWithParams(postId, userDefId));
    }

    @DeleteMapping(path = "/delete/{postLikeId}")
    @Override
    public RootEntity<Boolean> deletePostLike(@PathVariable(value = "postLikeId", required = true) String postLikeId) {
        return ok(postLikeService.deletePostLike(postLikeId));
    }

    @PutMapping(path = "/switch")
    @Override
    public RootEntity<Boolean> switchPostLike(@RequestBody @Valid DtoPostLikeIU dtoPostLikeIU) {
        return ok(postLikeService.switchPostLike(dtoPostLikeIU));
    }

    @PutMapping(path = "/switch-with-ws")
    @Override
    public void switchPostLikeAndStatus(@RequestBody @Valid DtoPostLikeIU dtoPostLikeIU) {
        postLikeService.switchPostLike(dtoPostLikeIU);
        PostLikeStatusResponse postLikeStatusResponse = postLikeService.findPostLikeStatus(dtoPostLikeIU.getPostId(), dtoPostLikeIU.getUserDefId());

        simpMessagingTemplate.convertAndSend("/topic/post-like/"+dtoPostLikeIU.getPostId() , postLikeStatusResponse);

    }

    @GetMapping(path = "/post-like-status")
    @Override
    public RootEntity<PostLikeStatusResponse> findPostLikeStatus(@RequestParam(value = "postId", required = false) String postId,
                                                                 @RequestParam(value = "userDefId", required = false) String userDefId) {
        return ok(postLikeService.findPostLikeStatus(postId , userDefId));
    }

    @MessageMapping("/post-like-status-with-ws")
    @Override
    public void findPostLikeStatusWithWs(@Payload DtoPostLikeIU dtoPostLikeIU){
        PostLikeStatusResponse postLikeResponse = postLikeService.findPostLikeStatus(dtoPostLikeIU.getPostId(), dtoPostLikeIU.getUserDefId());
        simpMessagingTemplate.convertAndSend("/topic/post-like-user/" + dtoPostLikeIU.getPostId()  + "/" + dtoPostLikeIU.getUserDefId() , postLikeResponse);
        simpMessagingTemplate.convertAndSend("/topic/post-like-post/" + dtoPostLikeIU.getPostId() , postLikeResponse);

    }
}
