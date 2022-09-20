package com.pavel.newsweb.Service.impl;

import com.pavel.newsweb.Dto.CommentDto;
import com.pavel.newsweb.Entity.CommentEntity;
import com.pavel.newsweb.Entity.NewsEntity;
import com.pavel.newsweb.Entity.UsersEntity;
import com.pavel.newsweb.Exception.BadRequestException;
import com.pavel.newsweb.Exception.NotFoundException;
import com.pavel.newsweb.Factories.AnswerFactories;
import com.pavel.newsweb.Factories.CommentFactories;
import com.pavel.newsweb.Model.Answer;
import com.pavel.newsweb.Repository.CommentRepository;
import com.pavel.newsweb.Repository.NewsRepository;
import com.pavel.newsweb.Repository.UsersRepository;
import com.pavel.newsweb.Service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {

    private final UsersRepository usersRepository;

    private final NewsRepository newsRepository;

    private final CommentRepository commentRepository;

    public CommentServiceImpl(UsersRepository usersRepository, NewsRepository newsRepository, CommentRepository commentRepository) {
        this.usersRepository = usersRepository;
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDto> GetAllComments() {
        return null;
    }

    @Override
    public CommentDto CreateComment(String username, Long news_id, CommentEntity commentEntity, BindingResult bindingResult) {
      UsersEntity users = usersRepository
               .findByUsername(username)
               .orElseThrow(() -> {
                   throw new NotFoundException("Not found for user");
               });
     NewsEntity news = newsRepository
               .findById(news_id)
               .orElseThrow(() -> {
                   throw new NotFoundException("Not found for news id");
               });
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors){
                throw new BadRequestException(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
       CommentEntity commentsave = commentRepository
               .saveAndFlush(
                       CommentEntity
                               .builder()
                               .description(commentEntity.getDescription())
                               .usersEntity(users)
                               .newsEntity(news)
                               .build()
               );
       return CommentFactories.MAKE_DTO(commentsave);
    }

    @Override
    public CommentDto EditComment(Long comment_id, CommentEntity commentEntity) {
        commentRepository
                .findById(comment_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Not found for comment id");
                });
        CommentEntity comment = commentRepository.save(commentEntity);
        return CommentFactories.MAKE_DTO(comment);
    }

    @Override
    public Answer DeleteComment(Long comment_id) {
        commentRepository
                .findById(comment_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Not found for comment id");
                });
        commentRepository.deleteById(comment_id);
        return AnswerFactories.answer(true);
    }
}
