package com.example.demo.service;

import com.example.demo.entity.ROLE;
import com.example.demo.entity.UserEntity;
import com.example.demo.repostiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService, UserDetailsWrapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> findAllEntities() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(null);
        user.setRole(ROLE.READER);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "not found");
        }
        return user;
    }

    @Override
    public boolean findByLogin(String login) {
        return userRepository.findByLogin(login) != null;
    }

    @Override
    public boolean findByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
