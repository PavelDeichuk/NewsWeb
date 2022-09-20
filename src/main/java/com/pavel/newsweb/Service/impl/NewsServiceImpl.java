package com.pavel.newsweb.Service.impl;

import com.pavel.newsweb.Dto.CategoryDto;
import com.pavel.newsweb.Dto.NewsDto;
import com.pavel.newsweb.Entity.CategoryEntity;
import com.pavel.newsweb.Entity.NewsEntity;
import com.pavel.newsweb.Entity.UsersEntity;
import com.pavel.newsweb.Exception.BadRequestException;
import com.pavel.newsweb.Exception.NotFoundException;
import com.pavel.newsweb.Factories.AnswerFactories;
import com.pavel.newsweb.Factories.CategoryFactories;
import com.pavel.newsweb.Factories.NewsFactories;
import com.pavel.newsweb.Model.Answer;
import com.pavel.newsweb.Repository.CategoryRepository;
import com.pavel.newsweb.Repository.NewsRepository;
import com.pavel.newsweb.Repository.UsersRepository;
import com.pavel.newsweb.Service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final UsersRepository usersRepository;

    private final CategoryRepository categoryRepository;

    public NewsServiceImpl(NewsRepository newsRepository, UsersRepository usersRepository, CategoryRepository categoryRepository) {
        this.newsRepository = newsRepository;
        this.usersRepository = usersRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<NewsDto> GetAllNews() {
        List<NewsEntity> newsEntities = newsRepository.findAll();
        if(newsEntities.isEmpty()){
            throw new NotFoundException("news list is empty!");
        }
        return newsEntities
                .stream()
                .map(NewsFactories::MAKE_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public NewsDto GetNewsById(Long id) {
        NewsEntity newsEntity = newsRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Not found for news id");
                });
        return NewsFactories.MAKE_DTO(newsEntity);
    }


    @Override
    public CategoryDto GetNewsByCategory(String category_name) {
       CategoryEntity category = categoryRepository
                .findByName(category_name)
                .orElseThrow(() -> {
                    throw new NotFoundException("Not found for category name");
                });
       return CategoryFactories.MAKE_DTO(category);
    }

    @Override
    public NewsDto CreateNews(NewsEntity newsEntity, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors){
                throw new BadRequestException(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
       NewsEntity newsEntity1 = newsRepository
               .saveAndFlush(
                       NewsEntity
                               .builder()
                               .id(newsEntity.getId())
                               .title(newsEntity.getTitle())
                               .description(newsEntity.getDescription())
                               .build()
               );
       return NewsFactories.MAKE_DTO(newsEntity1);
    }

    @Override
    public NewsDto EditNews(Long id, NewsEntity newsEntity) {
        newsRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Not found for news id");
                });
        NewsEntity news = newsRepository.save(newsEntity);
        return NewsFactories.MAKE_DTO(news);
    }

    @Override
    public Answer DeleteNews(Long id) {
        newsRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("Not found for news id");
                });
        newsRepository.deleteById(id);
        return AnswerFactories.answer(true);
    }
}
