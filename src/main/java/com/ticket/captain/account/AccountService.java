package com.ticket.captain.account;

import com.ticket.captain.config.AppProperties;
import com.ticket.captain.mail.EmailMessage;
import com.ticket.captain.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;
    private final EmailService emailService;

    public Account createAccount(SignUpForm signUpForm){
        Account newAccount = accountSample(signUpForm);
        sendSignUpConfirmEmail(newAccount);
        return accountRepository.save(newAccount);
    }

    private Account accountSample(SignUpForm signUpForm){
        Account account = new Account();
        account.setId(10L);
        account.setLoginId(signUpForm.getLoginId());
        account.setEmail(signUpForm.getEmail());
        account.setName(signUpForm.getName());
        account.setNickname(signUpForm.getNickname());
        account.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        account.setCreateDate(LocalDateTime.now());
        account.setPublicIp(getIpInfo()); // session 에 있는 값 가져오기
        account.generateEmailCheckToken();

        return account;
    }

    public void login(Account account){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public void completeSignUp(Account account) {
        account.completeSignUp();
        login(account);
    }

    public void sendSignUpConfirmEmail(Account newAccount){
        Context context = new Context();
        context.setVariable("link", "/check-email-token?token="+ newAccount.getEmail() +
                "&email=" +newAccount.getEmail());
        context.setVariable("name",newAccount.getName());
        context.setVariable("linkName", "회원가입 이메일 인증");
        context.setVariable("message", "티켓원정대 회원 가입을 위해서 링크를 클릭하세요");
        context.setVariable("host", appProperties.getHost());
        String message = templateEngine.process("mail/simple-link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(newAccount.getEmail())
                .subject("티켓 원정대, 회원 가입 인증")
                .message(message)
                .build();
        emailService.sendEmail(emailMessage);
    }

    private String getIpInfo(){
        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = req.getHeader("X-FORWARDED-FOR");
        if(ip == null){
            ip = req.getRemoteAddr();
        }
        return ip;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(emailOrNickname);

        if(account == null){
            account = accountRepository.findByNickname(emailOrNickname);
        }

        if(account == null){
            throw new UsernameNotFoundException(emailOrNickname);
        }
        return new UserAccount(account);
    }
}
