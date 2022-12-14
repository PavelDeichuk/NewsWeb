package com.pavel.newsweb.Service;

import com.pavel.newsweb.Dto.UsersDto;
import com.pavel.newsweb.Entity.UsersEntity;
import com.pavel.newsweb.Model.Answer;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UsersService {

    List<UsersDto> GetAllUsers();

    UsersDto FindById(Long id);

    UsersDto CreateUser(UsersEntity usersEntity, BindingResult bindingResult);

    UsersDto EditUser(Long id, UsersEntity usersEntity);

    Answer DeleteUser(Long id);

    Answer ActivateAccount(String activationcode);
}
