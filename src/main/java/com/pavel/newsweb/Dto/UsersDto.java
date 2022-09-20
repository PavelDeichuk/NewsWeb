package com.pavel.newsweb.Dto;

import com.pavel.newsweb.Entity.NewsEntity;
import lombok.*;

import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private Long id;

    private String username;

    private String email;

    private String roles;

    private List<NewsEntity> newsEntities;

}
