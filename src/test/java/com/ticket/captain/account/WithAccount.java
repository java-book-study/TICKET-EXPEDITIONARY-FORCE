package com.ticket.captain.account;


import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAccountSecurityContextFactory.class)
public @interface WithAccount {

    String email() default "testTicket@gmail.com";

    String nickname() default "testTicket";

    Role role() default Role.ROLE_USER;
}
