package com.example.week4.service;

import com.example.week4.mapper.UserMapper;
import com.example.week4.model.DefaultRes;
import com.example.week4.model.LoginReq;
import com.example.week4.model.User;
import com.example.week4.util.ResponseMessage;
import com.example.week4.util.StatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public DefaultRes join(User user){
        int idx = userMapper.join(user);
        return new DefaultRes(StatusCode.OK, ResponseMessage.CREATED_USER, idx);
    }

    public DefaultRes login(LoginReq loginReq){
        String name = userMapper.login(loginReq);
        return new DefaultRes(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, name);
    }

    public DefaultRes getAllUsers(){
        final List<User> users = userMapper.findAll();
        if(users.isEmpty()) return new DefaultRes(StatusCode.OK, ResponseMessage.NOT_FOUND_USER);
        return new DefaultRes(StatusCode.OK, ResponseMessage.READ_USER, users);
    }

    public DefaultRes getUser(String id){
        String name = userMapper.getUser(id);
        return new DefaultRes(StatusCode.OK, ResponseMessage.READ_USER, name);
    }
}
