package com.ticket.captain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated();
//                .mvcMatchers("/", "/login", "/sign-up", "/swagger/**").permitAll()
//                .mvcMatchers(HttpMethod.GET).permitAll()
//                .anyRequest().authenticated();
  
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
