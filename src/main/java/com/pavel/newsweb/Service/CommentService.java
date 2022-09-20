package com.pavel.newsweb.Service;

import com.pavel.newsweb.Dto.CommentDto;
import com.pavel.newsweb.Entity.CommentEntity;
import com.pavel.newsweb.Model.Answer;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CommentService {

    List<CommentDto> GetAllComments();

    CommentDto CreateComment(String username, Long news_id, CommentEntity commentEntity, BindingResult bindingResult);

    CommentDto EditComment(Long comment_id, CommentEntity commentEntity);

    Answer DeleteComment(Long comment_id);
}
