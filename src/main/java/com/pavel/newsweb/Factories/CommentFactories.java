package com.pavel.newsweb.Factories;

import com.pavel.newsweb.Dto.CommentDto;
import com.pavel.newsweb.Entity.CommentEntity;

public class CommentFactories {
    public static final CommentDto MAKE_DTO(CommentEntity commentEntity){
        return CommentDto
                .builder()
                .id(commentEntity.getId())
                .description(commentEntity.getDescription())
                .usersEntity(commentEntity.getUsersEntity())
                .newsEntity(commentEntity.getNewsEntity())
                .build();
    }
}
