package com.ticket.captain.config.security;

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
        web.ignoring().antMatchers("/**");
// 시큐리티 설정 해제위해 주석처리
//        web.ignoring()
//                .mvcMatchers("/node_modules/**")
//                .mvcMatchers("/h2-console/**")
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
