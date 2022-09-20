package com.pavel.newsweb.Controller;

import com.pavel.newsweb.Dto.CategoryDto;
import com.pavel.newsweb.Entity.CategoryEntity;
import com.pavel.newsweb.Model.Answer;
import com.pavel.newsweb.Repository.CategoryRepository;
import com.pavel.newsweb.Service.impl.CategoryServiceImpl;
import com.pavel.newsweb.Service.impl.KafkaSenderImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@Tag(name = "Category manage", description = "Category manage")
@Slf4j
public class CategoryController {
     private final CategoryServiceImpl categoryService;


     private final KafkaSenderImpl kafkaSender;

     private static final String CATEGORY_ID = "/{category_id}";

    private static final String ADDNEWSFORCATEGORY = "/news/{news_id}/category/{category_id}";

    public CategoryController(CategoryServiceImpl categoryService, KafkaSenderImpl kafkaSender) {
        this.categoryService = categoryService;
        this.kafkaSender = kafkaSender;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get All Categories", method = "Get", description = "Get All Categories")
    public List<CategoryDto> GetAllCategories(){
        log.info("Controller: Fetching category all");
        kafkaSender.SendMessage("newstopic", "Controller: Fetching category all");
        return categoryService.GetAllCategory();
    }

    @RequestMapping(value = CATEGORY_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Categories By Id", method = "Get", description = "Get Categories By Id")
    public CategoryDto GetCategoryById(@PathVariable Long category_id){
        log.info("Controller: Fetching category by id");
        kafkaSender.SendMessage("newstopic", "Controller: Fetching category by id " + category_id);
        return categoryService.GetCategoryById(category_id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Category", method = "Post", description = "Create Category")
    @SecurityRequirement(name = "Bearer Authentication")
    public CategoryDto CreateCategory(@Valid @RequestBody CategoryEntity category, BindingResult bindingResult){
        log.info("Controller: Fetching category create");
        kafkaSender.SendMessage("newstopic", "Controller : Fetching category create " + category.toString());
        return categoryService.CreateCategory(category, bindingResult);
    }

    @RequestMapping(value = ADDNEWSFORCATEGORY,method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add news for category", method = "Get", description = "Add news for category")
    @SecurityRequirement(name = "Bearer Authentication")
    public Answer AddNewsForCategory(@PathVariable Long news_id, @PathVariable Long category_id){
        log.info("Controller: UpdateCategoryNews");
        kafkaSender.SendMessage("newstopic", "Controller: UpdateCategoryNews");
        return categoryService.AddNewsForCategory(news_id,category_id);
    }

    @RequestMapping(value = CATEGORY_ID, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Edit Category", method = "Put", description = "Edit Category")
    @SecurityRequirement(name = "Bearer Authentication")
    public CategoryDto EditCategory(@PathVariable Long category_id,
                                    @Valid @RequestBody CategoryEntity categoryEntity){
        log.info("Controller: Fetching category edit " + category_id);
        kafkaSender.SendMessage("newstopic", "Controller : Fetching category edit " + category_id);
        return categoryService.EditCategory(category_id, categoryEntity);
    }

    @RequestMapping(value = CATEGORY_ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Category", method = "Delete", description = "Delete Category")
    @SecurityRequirement(name = "Bearer Authentication")
    public Answer DeleteCategory(@PathVariable Long category_id){
        log.info("Controller: Fetching category delete " + category_id);
        kafkaSender.SendMessage("newstopic", "Controller: Fetching category delete " + category_id);
        return categoryService.DeleteCategory(category_id);
    }

}
