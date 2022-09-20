package com.pavel.newsweb.Controller;

import com.pavel.newsweb.Model.JwtRequest;
import com.pavel.newsweb.Model.JwtResponse;
import com.pavel.newsweb.Service.impl.KafkaSenderImpl;
import com.pavel.newsweb.Service.impl.UsersDetailService;
import com.pavel.newsweb.Uitlity.JwtUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Jwt manage", description = "Jwt manage")
@Slf4j
public class AuthController {
    private static final String AUTH = "/auth";

    private final AuthenticationManager authenticationManager;

    private final UsersDetailService usersDetailService;

    private final JwtUtility jwtUtility;

    private final KafkaSenderImpl kafkaSender;

    public AuthController(AuthenticationManager authenticationManager, UsersDetailService usersDetailService, JwtUtility jwtUtility, KafkaSenderImpl kafkaSender) {
        this.authenticationManager = authenticationManager;
        this.usersDetailService = usersDetailService;
        this.jwtUtility = jwtUtility;
        this.kafkaSender = kafkaSender;
    }

    @RequestMapping(value = AUTH, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Jwt token", description = "Get Jwt token")
    public ResponseEntity<?> GetToken(@RequestBody JwtRequest jwtRequest){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        final UserDetails userDetails = usersDetailService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtUtility.generateToken(userDetails);
        JwtResponse tokens = new JwtResponse(token);
        log.info("Controller: Fetching get token");
        kafkaSender.SendMessage("newstopic", "Controller: Fetching get token");
        return new ResponseEntity(tokens, httpHeaders, HttpStatus.OK);
    }
}
