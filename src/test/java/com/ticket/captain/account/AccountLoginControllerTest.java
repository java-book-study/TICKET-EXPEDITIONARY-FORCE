package com.ticket.captain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.account.authentication.AuthenticationRequest;
import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.common.Address;
import com.ticket.captain.mail.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountLoginControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;
    @MockBean
    EmailService emailService;

    @Test
    @DisplayName("아이디 비밀번호 일치할 때의 로그인 테스트")
    public void loginTest() throws Exception{
        AccountCreateDto accountCreateDto = accountCreateDtoSample();
        accountSample();
        String principal = accountCreateDto.getEmail();
        String credentials = accountCreateDto.getPassword();
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                principal, credentials);

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않을 시의 로그인 테스트")
    public void authenticateFailedWithPasswordTest() throws Exception{
        AccountCreateDto accountCreateDto = accountCreateDtoSample();
        accountSample();
        String principal = accountCreateDto.getEmail();
        String credentials = "11111111";
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                principal, credentials);

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(unauthenticated())
                .andDo(print());
    }

    @Test
    @DisplayName("아이디가 일치하지 않을 시의 로그인 테스트")
    public void authenticateFailedWithIdTest() throws Exception{
        AccountCreateDto accountCreateDto = accountCreateDtoSample();
        accountSample();
        String principal = "kangsy@naver.com";
        String credentials = accountCreateDto.getPassword();
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                principal, credentials);

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(unauthenticated())
                .andDo(print());
    }

    private AccountCreateDto accountCreateDtoSample(){
        Address address = new Address("seoul", "gangnam", "111");
        return AccountCreateDto.builder()
                .email("kangsy763@naver.com")
                .password("1qaz2wsx")
                .nickname("bepoz")
                .name("seungyoon")
                .address(address)
                .build();
    }

    private Account accountSample() {

        AccountCreateDto accountCreateDto = accountCreateDtoSample();
        AccountDto accountDto = accountService.createAccount(accountCreateDto);
        Account newAccount = accountService.findByEmail(accountDto.getEmail());
        return newAccount;
    }
}