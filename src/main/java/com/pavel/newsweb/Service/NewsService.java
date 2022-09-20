package com.pavel.newsweb.Service;

import com.pavel.newsweb.Dto.CategoryDto;
import com.pavel.newsweb.Dto.NewsDto;
import com.pavel.newsweb.Entity.NewsEntity;
import com.pavel.newsweb.Model.Answer;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;

public interface NewsService {
    List<NewsDto> GetAllNews();

    NewsDto GetNewsById(Long id);


    CategoryDto GetNewsByCategory(String category_name);

    NewsDto CreateNews(NewsEntity newsEntity, BindingResult bindingResult);

    NewsDto EditNews(Long id, NewsEntity newsEntity);

    Answer DeleteNews(Long id);
}
