package com.ticket.captain.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .mvcMatchers("/", "/login","/sign-up/**").permitAll()
                    .mvcMatchers(HttpMethod.GET).permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .successHandler(new MyLoginSuccessHandler())
                    .failureHandler(new MyLoginFailureHandler())
                .and().httpBasic()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/denied")
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
