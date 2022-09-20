package com.pavel.newsweb.Factories;

import com.pavel.newsweb.Dto.UsersDto;
import com.pavel.newsweb.Entity.UsersEntity;

public class UsersFactories {
     public static final UsersDto MAKE_DTO(UsersEntity usersEntity){
         return UsersDto
                 .builder()
                 .id(usersEntity.getId())
                 .username(usersEntity.getUsername())
                 .email(usersEntity.getEmail())
                 .roles(usersEntity.getRoles())
                 .newsEntities(usersEntity.getNewsEntities())
                 .build();
     }
}
