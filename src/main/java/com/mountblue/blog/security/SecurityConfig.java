package com.mountblue.blog.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/post").permitAll()
                .antMatchers("/post/new").authenticated()
                .antMatchers("/post/{id}").permitAll()
                .antMatchers("/saveComment").authenticated()
                .antMatchers("/post/{postId}/comment/{commentId}").authenticated()
                .antMatchers("/post/update").authenticated()
                .antMatchers("/post/{id}").authenticated()
                .and().formLogin()
                .and().httpBasic();
    }
}

