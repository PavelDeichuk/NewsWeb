package com.pavel.newsweb.Controller;

import com.pavel.newsweb.Dto.CategoryDto;
import com.pavel.newsweb.Dto.NewsDto;
import com.pavel.newsweb.Entity.NewsEntity;
import com.pavel.newsweb.Model.Answer;
import com.pavel.newsweb.Service.impl.KafkaSenderImpl;
import com.pavel.newsweb.Service.impl.NewsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@Tag(name = "News manage", description = "News description")
@Slf4j
public class NewsController {

    private final NewsServiceImpl newsService;

    private final KafkaSenderImpl kafkaSender;


    private static final String NEWS_ID = "/{news_id}";

    private static final String GET_NEWS_CATEGORY = "/category";


    public NewsController(NewsServiceImpl newsService, KafkaSenderImpl kafkaSender) {
        this.newsService = newsService;
        this.kafkaSender = kafkaSender;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get All News", method = "Get", description = "Get All News")
    public List<NewsDto> GetAllNews(){
        log.info("Controller: Fetching news all");
        kafkaSender.SendMessage("newstopic", "Controller : Fetching news all");
       return newsService.GetAllNews();
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create News", method = "Post", description = "Create News")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    public NewsDto CreateNews(@Valid @RequestBody NewsEntity newsEntity, BindingResult bindingResult){
        log.info("Controller: Create News " + newsEntity);
        kafkaSender.SendMessage("newstopic", "Controller: Create News " + newsEntity.toString());
        return newsService.CreateNews(newsEntity, bindingResult);
    }

    @RequestMapping(value = GET_NEWS_CATEGORY, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create News", method = "Post", description = "Create News")
    @SecurityRequirement(name = "Bearer Authentication")
    public CategoryDto GetNewsByCategory(@RequestParam("name") String categories){
        log.info("Controller: Fetching news by category " + categories);
        kafkaSender.SendMessage("newstopic", "Controller: Fetching news by category " + categories );
        return newsService.GetNewsByCategory(categories);
    }

    @Transactional
    @RequestMapping(value = NEWS_ID, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Edit News", method = "Put", description = "Edit News")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    public NewsDto EditNews(@PathVariable Long news_id, @RequestBody NewsEntity newsEntity){
        log.info("Controller: Edit news " + news_id);
        kafkaSender.SendMessage("newstopic", "Controller: Edit news " + news_id);
        return newsService.EditNews(news_id,newsEntity);
    }

    @Transactional
    @RequestMapping(value = NEWS_ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete News", method = "Delete", description = "Delete News")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    public Answer DeleteNews(@PathVariable Long news_id){
        log.info("Controller: Delete news " + news_id);
        kafkaSender.SendMessage("newstopic", "Controller: Delete news " + news_id);
        return newsService.DeleteNews(news_id);
    }
}
