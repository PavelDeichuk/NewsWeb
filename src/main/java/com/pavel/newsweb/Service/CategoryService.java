package com.pavel.newsweb.Service;

import com.pavel.newsweb.Dto.CategoryDto;
import com.pavel.newsweb.Entity.CategoryEntity;
import com.pavel.newsweb.Model.Answer;
import org.springframework.validation.BindingResult;


import java.util.List;

public interface CategoryService {
    List<CategoryDto> GetAllCategory();

    CategoryDto GetCategoryById(Long category_id);

    CategoryDto CreateCategory(CategoryEntity categoryEntity, BindingResult bindingResult);

    Answer AddNewsForCategory(Long news_id, Long category_id);

    CategoryDto EditCategory(Long category_id, CategoryEntity categoryEntity);

    Answer DeleteCategory(Long category_id);
}
