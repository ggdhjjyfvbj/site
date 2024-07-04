package com.example.demo.repostiory;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);

    UserEntity findByEmail(String email);
}
