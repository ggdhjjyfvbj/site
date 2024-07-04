package com.example.demo.utils.accountUtils;

import com.example.demo.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserEntityUtils {

    public static UserEntity user() {
        UserEntity user = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserEntity) {
            user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }
}
