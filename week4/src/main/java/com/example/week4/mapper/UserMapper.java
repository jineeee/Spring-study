package com.example.week4.mapper;

import com.example.week4.model.LoginReq;
import com.example.week4.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("SELECT name FROM user WHERE id=#{id} AND pw=#{pw}")
    String login(final LoginReq loginReq);

    @Select("SELECT name FROM user WHERE id=#{id}")
    String getUser(String id);

    @Insert({"INSERT INTO user(name, id, pw) VALUES(#{name}, #{id}, #{pw})"})
    @Options(useGeneratedKeys = true, keyColumn = "idx")
    int join(final User user);
}
