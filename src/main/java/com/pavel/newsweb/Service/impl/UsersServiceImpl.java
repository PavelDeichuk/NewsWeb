package com.pavel.newsweb.Service.impl;

import com.pavel.newsweb.Dto.UsersDto;
import com.pavel.newsweb.Entity.UsersEntity;
import com.pavel.newsweb.Factories.AnswerFactories;
import com.pavel.newsweb.Factories.UsersFactories;
import com.pavel.newsweb.Model.Answer;
import com.pavel.newsweb.Repository.UsersRepository;
import com.pavel.newsweb.Service.UsersService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<UsersDto> GetAllUsers() {
        List<UsersEntity> usersEntities = usersRepository.findAll();
        if(usersEntities.isEmpty()){
            throw new RuntimeException("Users list is empty!");
        }
        return usersEntities
                .stream()
                .map(UsersFactories::MAKE_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsersDto FindById(Long id) {
        UsersEntity users = usersRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found for user id");
                });
        return UsersFactories.MAKE_DTO(users);
    }

    @Override
    public UsersDto CreateUser(UsersEntity usersEntity) {
        usersRepository
                .findByUsername(usersEntity.getUsername())
                .ifPresent(username -> {
                    throw new RuntimeException("Username is exist!");
                });
        usersRepository
                .findByEmail(usersEntity.getEmail())
                .ifPresent(email -> {
                    throw new RuntimeException("Email is exist");
                });
        if(!Objects.equals(usersEntity.getPassword(), usersEntity.getPassword2())){
            throw new RuntimeException("Password is not equals");
        }
        UsersEntity users = usersRepository
                .saveAndFlush(
                        UsersEntity
                                .builder()
                                .username(usersEntity.getUsername())
                                .password(usersEntity.getPassword())
                                .email(usersEntity.getEmail())
                                .roles("QUEST")
                                .activationcode(UUID.randomUUID().toString())
                                .build()
                );
        return UsersFactories.MAKE_DTO(users);
    }

    @Override
    public UsersDto EditUser(Long id, UsersEntity usersEntity) {
        usersRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found for user id");
                });
        UsersEntity users = usersRepository.save(usersEntity);
        return UsersFactories.MAKE_DTO(users);
    }

    @Override
    public Answer DeleteUser(Long id) {
        usersRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Not found for user id");
                });
        usersRepository.deleteById(id);
        return AnswerFactories.answer(true);
    }

    @Override
    public UsersDto ActivateAccount(String activationcode) {
      UsersEntity users = usersRepository.findByActivationcode(activationcode);
      if(users == null){
          throw new RuntimeException("Account is activated!");
      }
      users.setActivationcode(null);
      users.setRoles("USER");
     UsersEntity usersave = usersRepository.save(users);
      return UsersFactories.MAKE_DTO(usersave);
    }
}
