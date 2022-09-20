package com.pavel.newsweb.Controller;

import com.pavel.newsweb.Dto.UsersDto;
import com.pavel.newsweb.Entity.UsersEntity;
import com.pavel.newsweb.Model.Answer;
import com.pavel.newsweb.Service.impl.KafkaSenderImpl;
import com.pavel.newsweb.Service.impl.UsersServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "users manage", description = "users manage")
@Slf4j
public class UsersController {
    private final UsersServiceImpl usersService;

    private final KafkaSenderImpl kafkaSender;


    private static final String USERS_ID = "/{user_id}";

    private static final String ACTIVATE_ACCOUNT = "/activate/{activation_code}";

    public UsersController(UsersServiceImpl usersService, KafkaSenderImpl kafkaSender) {
        this.usersService = usersService;
        this.kafkaSender = kafkaSender;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all users", method = "Get", description = "Get All Users")
    public List<UsersDto> GetAllUsers(){
        log.info("Controller: Fetching users all");
        kafkaSender.SendMessage("newstopic", "Controller: Fetching users all");
        return usersService.GetAllUsers();
    }

    @RequestMapping(value = USERS_ID,method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get User by id", method = "Get", description = "Get user by id")
    public UsersDto GetUserById(@PathVariable Long user_id){
        log.info("Controller: Fetching user by id " + user_id);
        kafkaSender.SendMessage("newstopic", "Controller: Fetching user by id " + user_id);
        return usersService.FindById(user_id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create User", method = "Post", description = "Create User")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    public UsersDto CreateUsers(@Valid @RequestBody UsersEntity usersEntity, BindingResult bindingResult){
        log.info("Controller: Create user " + usersEntity);
        kafkaSender.SendMessage("newstopic", "Controller: Create user " + usersEntity);
        return usersService.CreateUser(usersEntity, bindingResult);
    }
    @Transactional
    @RequestMapping(value = USERS_ID, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Edit User", method = "Put", description = "Edit User")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    public UsersDto EditUsers(@RequestBody UsersEntity usersEntity, @PathVariable Long user_id){
        log.info("Controller: Edit user");
        kafkaSender.SendMessage("newstopic", "Controller: Edit user " + usersEntity);
        return usersService.EditUser(user_id, usersEntity);
    }
    @Transactional
    @RequestMapping(value = USERS_ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete User By id", method = "Delete", description = "Delete User")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('USER')")
    public Answer DeleteUsers(@PathVariable Long user_id){
        log.info("Controller: Delete user " + user_id);
        kafkaSender.SendMessage("newstopic", "Controller: Delete user " + user_id);
        return usersService.DeleteUser(user_id);
    }

    @Transactional
    @RequestMapping(value = ACTIVATE_ACCOUNT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Activation account", method = "Get", description = "Activate Account")
    public Answer ActivateAccount(@PathVariable String activation_code){
        log.info("Controller: Activate user " + activation_code);
        kafkaSender.SendMessage("newstopic", "Controller: Activate user" + activation_code );
        return usersService.ActivateAccount(activation_code);
    }
}
