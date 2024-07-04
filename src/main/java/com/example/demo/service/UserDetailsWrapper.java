package com.example.demo.service;

import com.example.demo.entity.UserEntity;

import java.util.List;

public interface UserDetailsWrapper {

    List<UserEntity> findAllEntities();

    void saveUser(UserEntity user);

    boolean findByLogin(String login);

    boolean findByEmail(String email);
}
