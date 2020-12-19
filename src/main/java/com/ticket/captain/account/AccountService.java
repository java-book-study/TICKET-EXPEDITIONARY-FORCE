package com.ticket.captain.account;

import com.ticket.captain.account.dto.*;
import com.ticket.captain.config.AppProperties;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.exception.UnauthorizedException;
import com.ticket.captain.mail.EmailMessage;
import com.ticket.captain.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public AccountDto createAccount(AccountCreateDto accountCreateDto){
        Account newAccount = accountCreateDto.toEntity();
        newAccount.setPassword(passwordEncoder.encode(accountCreateDto.getPassword()));
        newAccount.generateEmailCheckToken();
        sendSignUpConfirmEmail(newAccount);
        return AccountDto.of(accountRepository.save(newAccount));
    }

    public void sendSignUpConfirmEmail(Account newAccount){
        Context context = new Context();
        context.setVariable("link", "/check-email-token?token="+ newAccount.getEmailCheckToken() +
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Account account = accountRepository.findByEmail(email);

        if (account == null) {
            throw new UsernameNotFoundException(email);
        }

        if (account.getRole() == null) {
            account.addRole(Role.UNAUTH);
        }

        return new UserAccount(account);
    }

    public Page<AccountDto> findAccountList(Pageable pageable) {

        return accountRepository.findAll(pageable).map(AccountDto::of);
    }

    public AccountDto findAccountDetail(Long id){
        Account account = accountRepository.findById(id).orElseThrow(NotFoundException::new);
        return AccountDto.of(account);
    }

    public AccountPutDto accountUpdate(Long id, AccountUpdateDto updateRequestDto) {

        Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        long update = account.update(updateRequestDto);
        return new AccountPutDto(update);
    }

    public AccountRoleDto roleAppoint(Long id, Role role) {

        Account account = accountRepository.findById(id).orElseThrow(NotFoundException::new);
        account.addRole(role);

        return AccountRoleDto.of(account);
    }

    public Account authenticate(String email, String password) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new NotFoundException();
        }

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new UnauthorizedException();
        }

        return account;
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public void completeSignUp(Account account, String token) {

        checkNotNull(account, "account must be provided.");
        checkNotNull(token, "token must be provided.");
        if (!account.isValidToken(token)) {
            throw new UnauthorizedException();
        }

        account.completeSignUp();
    }
}
