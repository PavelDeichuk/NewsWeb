package com.pavel.newsweb.Dto;

import com.pavel.newsweb.Entity.NewsEntity;
import com.pavel.newsweb.Entity.UsersEntity;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;

    private String description;

    private UsersEntity usersEntity;

    private NewsEntity newsEntity;
}
