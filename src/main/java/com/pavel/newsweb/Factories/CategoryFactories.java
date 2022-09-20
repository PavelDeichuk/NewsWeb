package com.pavel.newsweb.Factories;

import com.pavel.newsweb.Dto.CategoryDto;
import com.pavel.newsweb.Entity.CategoryEntity;

public class CategoryFactories {
    public static final CategoryDto MAKE_DTO(CategoryEntity categoryEntity){
       return CategoryDto
               .builder()
               .id(categoryEntity.getId())
               .name(categoryEntity.getName())
               .newsEntity(categoryEntity.getNewsEntity())
               .build();
    }
}
