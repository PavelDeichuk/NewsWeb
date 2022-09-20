package com.pavel.newsweb.Service.impl;

import com.pavel.newsweb.Dto.UsersDto;
import com.pavel.newsweb.Entity.UsersEntity;
import com.pavel.newsweb.Exception.BadRequestException;
import com.pavel.newsweb.Exception.NotFoundException;
import com.pavel.newsweb.Factories.AnswerFactories;
import com.pavel.newsweb.Factories.UsersFactories;
import com.pavel.newsweb.Model.Answer;
import com.pavel.newsweb.Repository.UsersRepository;
import com.pavel.newsweb.Service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    private final EmailServiceImpl emailService;

    public UsersServiceImpl(UsersRepository usersRepository, EmailServiceImpl emailService) {
        this.usersRepository = usersRepository;
        this.emailService = emailService;
    }

    @Override
    public List<UsersDto> GetAllUsers() {
        List<UsersEntity> usersEntities = usersRepository.findAll();
        if(usersEntities.isEmpty()){
            throw new NotFoundException("Users list is empty!");
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
                    throw new NotFoundException("Not found for user id");
                });
        return UsersFactories.MAKE_DTO(users);
    }

    @Override
    public UsersDto CreateUser(UsersEntity usersEntity, BindingResult bindingResult) {
        usersRepository
                .findByUsername(usersEntity.getUsername())
                .ifPresent(username -> {
                    throw new BadRequestException("Username is exist!");
                });
        usersRepository
                .findByEmail(usersEntity.getEmail())
                .ifPresent(email -> {
                    throw new BadRequestException("Email is exist");
                });
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors){
                throw new BadRequestException(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
        if(!Objects.equals(usersEntity.getPassword(), usersEntity.getPassword2())){
            throw new BadRequestException("Password is not equals");
        }
        emailService.Send(usersEntity.getEmail(), "Activate account", "Your activation code is: " + usersEntity.getActivationcode());
        UsersEntity users = usersRepository
                .saveAndFlush(
                        UsersEntity
                                .builder()
                                .username(usersEntity.getUsername())
                                .password(usersEntity.getPassword())
                                .email(usersEntity.getEmail())
                                .roles("QUEST")
                                .activationcode(usersEntity.getActivationcode())
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
                    throw new NotFoundException("Not found for user id");
                });
        usersRepository.deleteById(id);
        return AnswerFactories.answer(true);
    }

    @Override
    public Answer ActivateAccount(String activationcode) {
      UsersEntity users = usersRepository.findByActivationcode(activationcode);
      if(users == null){
          throw new BadRequestException("Account is activated!");
      }
      users.setActivationcode(null);
      users.setRoles("USER");
     UsersEntity usersave = usersRepository.save(users);
      return AnswerFactories.answer(true);
    }
}
