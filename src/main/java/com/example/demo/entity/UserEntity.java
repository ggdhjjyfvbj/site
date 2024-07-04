package com.example.demo.entity;

import com.example.demo.entity.forums.ForumEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Это обязательное поле")
    @Column(nullable = false, unique = true)
    private String login;

    @NotBlank(message = "Это обязательное поле")
    @Column(nullable = false, unique = true)
    private String email;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @NotNull(message = "Это обязательное поле")
    private Date date;

    @NotBlank(message = "Это обязательное поле")
    @Column(nullable = false)
    private String password;

    private String confirmPassword;

    private ROLE role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity")
    public List<ForumEntity> forumEntityList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public List<ChatMessageEntity> chatMessageEntities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(role.name().split(", ")).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
