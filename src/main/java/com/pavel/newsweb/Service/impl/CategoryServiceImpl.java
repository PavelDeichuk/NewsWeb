package com.pavel.newsweb.Service.impl;

import com.pavel.newsweb.Dto.CategoryDto;
import com.pavel.newsweb.Entity.CategoryEntity;
import com.pavel.newsweb.Entity.NewsEntity;
import com.pavel.newsweb.Exception.BadRequestException;
import com.pavel.newsweb.Exception.NotFoundException;
import com.pavel.newsweb.Factories.AnswerFactories;
import com.pavel.newsweb.Factories.CategoryFactories;
import com.pavel.newsweb.Factories.NewsFactories;
import com.pavel.newsweb.Model.Answer;
import com.pavel.newsweb.Repository.CategoryRepository;
import com.pavel.newsweb.Repository.NewsRepository;
import com.pavel.newsweb.Service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final NewsRepository newsRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, NewsRepository newsRepository) {
        this.categoryRepository = categoryRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public List<CategoryDto> GetAllCategory() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        if(categoryEntities.isEmpty()){
            throw new RuntimeException("Not found for list category list");
        }
        return categoryEntities
                .stream()
                .map(CategoryFactories::MAKE_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto GetCategoryById(Long category_id) {
        CategoryEntity category = categoryRepository
                .findById(category_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Not found for category id");
                });
        return CategoryFactories.MAKE_DTO(category);
    }

    @Override
    public CategoryDto CreateCategory(CategoryEntity categoryEntity, BindingResult bindingResult) {
        categoryRepository
                .findByName(categoryEntity.getName())
                .orElseThrow(() -> {
                    throw new BadRequestException("Name category is exist");
                });
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors){
                throw new BadRequestException(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
        CategoryEntity category = categoryRepository
                .saveAndFlush(
                        CategoryEntity
                                .builder()
                                .name(categoryEntity.getName())
                                .build()
                );
        return CategoryFactories.MAKE_DTO(category);
    }

    @Override
    public Answer AddNewsForCategory(Long news_id, Long category_id) {
        NewsEntity news = newsRepository
                .findById(news_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Not found for news id");
                });
        CategoryEntity category = categoryRepository
                .findById(category_id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Not found for category id");
                });
        List<NewsEntity> categoryNewsEntity = category.getNewsEntity();
        categoryNewsEntity.add(news);
        category.setNewsEntity(categoryNewsEntity);
        categoryRepository.save(category);
        return AnswerFactories.answer(true);
    }


    @Override
    public CategoryDto EditCategory(Long category_id, CategoryEntity categoryEntity) {
        categoryRepository
                .findById(category_id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found for category id");
                });
        CategoryEntity category = categoryRepository.save(categoryEntity);
        return CategoryFactories.MAKE_DTO(category);
    }

    @Override
    public Answer DeleteCategory(Long category_id) {
        categoryRepository
                .findById(category_id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found for category id");
                });
        categoryRepository.deleteById(category_id);
        return AnswerFactories.answer(true);

    }
}
