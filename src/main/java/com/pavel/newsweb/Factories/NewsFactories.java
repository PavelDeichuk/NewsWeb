package com.pavel.newsweb.Factories;

import com.pavel.newsweb.Dto.NewsDto;
import com.pavel.newsweb.Entity.NewsEntity;

public class NewsFactories {
    public static final NewsDto MAKE_DTO(NewsEntity newsEntity){
        return NewsDto
                .builder()
                .id(newsEntity.getId())
                .title(newsEntity.getTitle())
                .description(newsEntity.getDescription())
                .createby(newsEntity.getCreateby())
                .updateby(newsEntity.getUpdateby())
                .build();
    }
}
