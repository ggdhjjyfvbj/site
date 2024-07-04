package com.example.demo.config;

import com.example.demo.entity.ROLE;
import com.example.demo.service.MyUserDetailsService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers( "/welcome", "/forum/search", "news/search", "/forum/read", "/forum", "news/more", "/imgs/**", "/style/**", "/news").permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers("/register", "/login").anonymous())
                .authorizeHttpRequests(request -> request.requestMatchers("/create-topic", "/forum/comment").fullyAuthenticated())
                .authorizeHttpRequests(request -> request.requestMatchers("/create-news").hasAnyAuthority(ROLE.ADMIN.name(), ROLE.READER.name()))
                .formLogin(login -> login.loginPage("/login").defaultSuccessUrl("/welcome", true))
                .logout(logout -> logout.logoutUrl("/logout").permitAll());

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }
}
