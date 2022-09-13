package com.pavel.newsweb.Dto;

import lombok.*;

import javax.persistence.Transient;

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

}
