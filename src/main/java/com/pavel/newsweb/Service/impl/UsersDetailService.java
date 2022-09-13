package com.pavel.newsweb.Service.impl;

import com.pavel.newsweb.Entity.UsersEntity;
import com.pavel.newsweb.Repository.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public UsersDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity usersEntity = usersRepository
                .findByUsername(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Not found username");
                });
        UserDetails userDetails = User
                .builder()
                .username(usersEntity.getUsername())
                .password(usersEntity.getPassword())
                .roles(usersEntity.getRoles())
                .build();
        return userDetails;
    }
}
