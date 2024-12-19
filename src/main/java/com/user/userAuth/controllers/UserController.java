package com.user.userAuth.controllers;

import com.user.userAuth.dtos.LoginRequestDto;
import com.user.userAuth.dtos.SignupRequestDto;
import com.user.userAuth.exceptions.InvalidCredentialsException;
import com.user.userAuth.models.Token;
import com.user.userAuth.models.User;
import com.user.userAuth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class UserController {

    @Autowired
    UserService userService;

    /*******************************************************************************************************/
    @PostMapping("/signup")
    public User signUp(@RequestBody SignupRequestDto signupRequestDto){
        String name = signupRequestDto.getName();
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();
        return userService.signUp(name, email, password);

    }

    /*******************************************************************************************************/
    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto){

        try {

            String email = loginRequestDto.getEmail();
            String password = loginRequestDto.getPassword();
            return userService.login(email, password);

        }catch (InvalidCredentialsException e){

            System.out.println(e.getMessage());
        }
        return null ;
    }

    /*******************************************************************************************************/
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String token){
        userService.logout(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
