package com.pavel.newsweb.Service.impl;

import com.pavel.newsweb.Dto.UsersDto;
import com.pavel.newsweb.Entity.UsersEntity;
import com.pavel.newsweb.Service.UsersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {


    @Override
    public List<UsersDto> GetAllUsers() {
        return null;
    }

    @Override
    public UsersDto FindById(Long id) {
        return null;
    }

    @Override
    public UsersDto CreateUser(UsersEntity usersEntity) {
        return null;
    }

    @Override
    public UsersDto EditUser(Long id, UsersEntity usersEntity) {
        return null;
    }

    @Override
    public UsersDto DeleteUser(Long id) {
        return null;
    }

    @Override
    public UsersDto ActivateAccount(String activationcode) {
        return null;
    }
}
