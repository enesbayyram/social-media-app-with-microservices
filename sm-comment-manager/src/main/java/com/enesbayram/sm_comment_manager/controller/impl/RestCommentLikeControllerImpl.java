package com.enesbayram.sm_comment_manager.controller.impl;

import com.enesbayram.sm_comment_manager.controller.IRestCommentLikeController;
import com.enesbayram.sm_comment_manager.service.ICommentLikeService;
import com.enesbayram.sm_core.base.dto.DtoCommentLike;
import com.enesbayram.sm_core.base.dto.DtoCommentLikeIU;
import com.enesbayram.sm_core.base.model.CommentLike;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_core.base.restservice.base.RestBaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment-like")
public class RestCommentLikeControllerImpl extends RestBaseController implements IRestCommentLikeController {

    private final ICommentLikeService commentLikeService;

    @PostMapping(path = "/save")
    @Override
    public RootEntity<DtoCommentLike> saveCommentLike(@RequestBody @Valid DtoCommentLikeIU dtoCommentLikeIU) {
        return ok(commentLikeService.toDTO(commentLikeService.saveCommentLike(dtoCommentLikeIU)));
    }

    @DeleteMapping(path = "/delete/{commentLikeId}")
    @Override
    public RootEntity<Boolean> deleteCommentLike(@PathVariable(value = "commentLikeId", required = true) String commentLikeId) {
        return ok(commentLikeService.deleteCommentLike(commentLikeId));
    }

    @GetMapping(path = "/list/with-params")
    @Override
    public RootEntity<List<DtoCommentLike>> findCommentLikeWithParams(@RequestParam(value = "postId", required = false) String postId,
                                                                      @RequestParam(value = "userDefId", required = false) String userDefId,
                                                                      @RequestParam(value = "commentId", required = false) String commentId) {
        return ok(commentLikeService.findCommentLikeWithParams(postId, userDefId, commentId));
    }

    @GetMapping(path = "/list/count")
    @Override
    public RootEntity<Integer> getCommentLikeCount(@RequestParam(value = "postId", required = false) String postId,
                                                   @RequestParam(value = "userDefId", required = false) String userDefId,
                                                   @RequestParam(value = "commentId", required = false) String commentId) {
        return ok(commentLikeService.getCommentLikeCount(postId , userDefId , commentId));
    }
}
