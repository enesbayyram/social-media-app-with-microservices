package com.enesbayram.sm_comment_manager.controller.impl;

import com.enesbayram.sm_comment_manager.controller.IRestCommentsController;
import com.enesbayram.sm_comment_manager.dto.ChatMessage;
import com.enesbayram.sm_comment_manager.dto.DeleteCommentRequest;
import com.enesbayram.sm_comment_manager.service.ICommentsService;
import com.enesbayram.sm_core.base.dto.DtoComments;
import com.enesbayram.sm_core.base.dto.DtoCommentsIU;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_core.base.restservice.base.RestBaseController;
import com.enesbayram.sm_core.base.utils.JSONUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
public class RestCommentsControllerImpl extends RestBaseController implements IRestCommentsController {

    private final ICommentsService commentsService;

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping(path = "/save")
    @Override
    public RootEntity<DtoComments> saveComment(@RequestBody @Valid DtoCommentsIU dtoCommentsIU) {
        return ok(commentsService.toDTO(commentsService.saveComment(dtoCommentsIU)));
    }

    @MessageMapping("/save-comment-with-ws")
    @Override
    public void saveCommentWithWS(@Payload DtoCommentsIU dtoCommentsIU) {
        commentsService.toDTO(commentsService.saveComment(dtoCommentsIU));
        List<DtoComments> dtoCommentsList = commentsService.findCommentsByPostId(dtoCommentsIU.getPostId());

        messagingTemplate.convertAndSend("/topic/comments" ,dtoCommentsList);
    }

    @GetMapping(path = "/list/by-postid/{postId}")
    @Override
    public RootEntity<List<DtoComments>> findCommentsByPostId(@PathVariable(value = "postId", required = true) String postId) {
        return ok(commentsService.findCommentsByPostId(postId));
    }

    @DeleteMapping(path = "/delete/{commentId}")
    @Override
    public RootEntity<Boolean> deleteCommentRecursive(@PathVariable(value = "commentId", required = true) String commentId) {
        return ok(commentsService.deleteCommentRecursive(commentId));
    }

    @MessageMapping("/delete-comment-with-ws")
    @Override
    public RootEntity<Boolean> deleteCommentWithWS(@Payload DeleteCommentRequest request) {
        commentsService.deleteCommentRecursive(request.getCommentId());
        List<DtoComments> dtoCommentsList = commentsService.findCommentsByPostId(request.getPostId());

        messagingTemplate.convertAndSend("/topic/comments" ,dtoCommentsList);
        return ok(true);
    }
}
