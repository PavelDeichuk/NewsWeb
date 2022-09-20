package com.pavel.newsweb.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name is null!")
    @Size(min = 3, max = 24, message = "size name is min 3, max 24")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "news_category",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnore
    private List<NewsEntity> newsEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NewsEntity> getNewsEntity() {
        return newsEntity;
    }

    public void setNewsEntity(List<NewsEntity> newsEntity) {
        this.newsEntity = newsEntity;
    }
}
