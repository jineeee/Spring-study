package com.example.week4.controller;

import com.example.week4.model.LoginReq;
import com.example.week4.model.User;
import com.example.week4.service.UserService;
import com.example.week4.util.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("users")
    public ResponseEntity join(@RequestBody User user) {
        try {
            return new ResponseEntity(userService.join(user), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(ResponseMessage.CREATED_USER_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("users")
    public ResponseEntity getAllUsers(){
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity getUser(@PathVariable("id") String id){
        try {
            return new ResponseEntity(userService.getUser(id), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(ResponseMessage.NOT_FOUND_USER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginReq loginReq) {
        try {
            return new ResponseEntity(userService.login(loginReq), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(ResponseMessage.LOGIN_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
