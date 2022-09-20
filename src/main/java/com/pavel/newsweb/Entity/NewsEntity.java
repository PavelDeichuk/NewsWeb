package com.pavel.newsweb.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "news")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title null!")
    @Size(min = 4, max = 256, message = "title size is min 4, max 256!")
    private String title;

    @NotNull(message = "Description null!")
    @Size(min = 3, max = 1024, message = "description size is min 3, max 1024")
    private String description;

    @UpdateTimestamp
    private LocalDateTime updateby;

    @CreationTimestamp
    private LocalDateTime createby;

    @ManyToMany(mappedBy = "newsEntity")
    @ToString.Exclude
    @JsonIgnore
    private List<CategoryEntity> categoryEntities;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @ToString.Exclude
    private UsersEntity usersEntities;

}