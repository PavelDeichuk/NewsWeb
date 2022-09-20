package com.pavel.newsweb.Controller;

import com.pavel.newsweb.Dto.CommentDto;
import com.pavel.newsweb.Entity.CommentEntity;
import com.pavel.newsweb.Model.Answer;
import com.pavel.newsweb.Service.impl.CommentServiceImpl;
import com.pavel.newsweb.Service.impl.KafkaSenderImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "Comment manage", description = "Comment manage")
@Slf4j
public class CommentController {

    private final CommentServiceImpl commentService;

    private final KafkaSenderImpl kafkaSender;

    private static final String CREATE_COMMENT = "/news/{news_id}";

    private static final String COMMENT_ID = "/{comment_id}";

    public CommentController(CommentServiceImpl commentService, KafkaSenderImpl kafkaSender) {
        this.commentService = commentService;
        this.kafkaSender = kafkaSender;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get All Comments", method = "Get", description = "Get All Comments")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<CommentDto> GetAllComments() {
        log.info("Controller: Fetching comment all");
        kafkaSender.SendMessage("newstopic", "Controller: Fetching comment all");
        return commentService.GetAllComments();
    }

    @RequestMapping(value = CREATE_COMMENT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create comment", method = "Post", description = "Create comment")
    @SecurityRequirement(name = "Bearer Authentication")
    public CommentDto CreateComments(Principal principal,
                                     @PathVariable Long news_id,
                                     @Valid @RequestBody CommentEntity commentEntity,
                                     BindingResult bindingResult) {
        log.info("Controller: Create comment " + commentEntity.toString());
        kafkaSender.SendMessage("newstopic", "Controller: Create comment " + commentEntity.toString());
        return commentService.CreateComment(principal.getName(), news_id, commentEntity, bindingResult);
    }

    @RequestMapping(value = COMMENT_ID, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Edit comment", method = "Put", description = "Edit comment")
    @SecurityRequirement(name = "Bearer Authentication")
    public CommentDto EditComment(@Valid @RequestBody CommentEntity commentEntity,
                                  @PathVariable Long comment_id,
                                  Principal principal) {
        log.info("Controller: Fetching edit comment " + comment_id);
        kafkaSender.SendMessage("newstopic", "Controller: Fetching edit comment " + comment_id);
        return commentService.EditComment(comment_id, commentEntity);
    }

    @RequestMapping(value = COMMENT_ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create comment", method = "Post", description = "Create comment")
    @SecurityRequirement(name = "Bearer Authentication")
    public Answer DeleteComment(@PathVariable Long comment_id) {
        log.info("Controller: Fetching delete comment " + comment_id);
        kafkaSender.SendMessage("newstopic", "Controller: Fetching delete comment: " + comment_id);
        return commentService.DeleteComment(comment_id);
    }

}