package com.pavel.newsweb.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title null!")
    private String title;

    @NotNull(message = "Description null!")
    private String description;

    @UpdateTimestamp
    private LocalDateTime updateby;

    @CreationTimestamp
    private LocalDateTime createby;

    @ManyToMany(mappedBy = "newsEntity")
    @JsonIgnore
    private List<CategoryEntity> categoryEntities;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "news_users",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<UsersEntity> usersEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getUpdateby() {
        return updateby;
    }

    public void setUpdateby(LocalDateTime updateby) {
        this.updateby = updateby;
    }

    public LocalDateTime getCreateby() {
        return createby;
    }

    public void setCreateby(LocalDateTime createby) {
        this.createby = createby;
    }

    public List<CategoryEntity> getCategoryEntities() {
        return categoryEntities;
    }

    public void setCategoryEntities(List<CategoryEntity> categoryEntities) {
        this.categoryEntities = categoryEntities;
    }

    public List<UsersEntity> getUsersEntities() {
        return usersEntities;
    }

    public void setUsersEntities(List<UsersEntity> usersEntities) {
        this.usersEntities = usersEntities;
    }

}
