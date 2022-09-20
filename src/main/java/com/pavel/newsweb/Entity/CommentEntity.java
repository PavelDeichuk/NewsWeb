package com.pavel.newsweb.Entity;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "Comment description is min 3")
    @NotNull(message = "description is null!")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UsersEntity usersEntity;

    @ManyToOne
    @JoinColumn(name = "news_id")
    @ToString.Exclude
    private NewsEntity newsEntity;
}
