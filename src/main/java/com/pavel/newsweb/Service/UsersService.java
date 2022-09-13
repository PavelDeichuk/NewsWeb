package com.pavel.newsweb.Service;

import com.pavel.newsweb.Dto.UsersDto;
import com.pavel.newsweb.Entity.UsersEntity;

import java.util.List;

public interface UsersService {

    List<UsersDto> GetAllUsers();

    UsersDto FindById(Long id);

    UsersDto CreateUser(UsersEntity usersEntity);

    UsersDto EditUser(Long id, UsersEntity usersEntity);

    UsersDto DeleteUser(Long id);

    UsersDto ActivateAccount(String activationcode);
}
