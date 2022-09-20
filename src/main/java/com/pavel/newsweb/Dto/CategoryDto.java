package com.pavel.newsweb.Dto;

import com.pavel.newsweb.Entity.NewsEntity;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    private String name;

    private List<NewsEntity> newsEntity;
}
