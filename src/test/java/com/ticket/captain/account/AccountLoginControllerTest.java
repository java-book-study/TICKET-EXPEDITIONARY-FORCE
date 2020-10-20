package com.ticket.captain.account;


import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.mail.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountLoginControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;

//    @Test
//    @DisplayName("아이디 비밀번호 일치할 때의 로그인 테스트")
//    public void loginTest() throws Exception{
//        //given
//        AccountCreateDto accountCreateDto = accountCreateDtoSample();
//
//        //when
//        accountService.createAccount(accountCreateDto);
//
//        //then
//        mockMvc.perform(formLogin().user("kangsy763@naver.com").password("1qaz2wsx"))
//                .andExpect(authenticated().withUsername("kangsy763@naver.com"))
//                .andDo(print())
//        ;
//    }

    @Test
    @DisplayName("비밀번호가 일치하지 않을 시의 로그인 테스트")
    public void authenticateFailedWithPasswordTest() throws Exception{
        //given
        AccountCreateDto accountCreateDto = accountCreateDtoSample();

        //when
        accountService.createAccount(accountCreateDto);

        //then
        mockMvc.perform(formLogin().user("kangsy763@naver.com").password("asdfasdf"))
                .andExpect(unauthenticated())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("아이디가 일치하지 않을 시의 로그인 테스트")
    public void authenticateFailedWithIdTest() throws Exception{
        //given
        AccountCreateDto accountCreateDto = accountCreateDtoSample();

        //when
        accountService.createAccount(accountCreateDto);

        //then
        mockMvc.perform(formLogin().user("kangsy763@gmail.com").password("1qaz2wsx"))
                .andExpect(unauthenticated())
                .andDo(print())
        ;
    }

    private AccountCreateDto accountCreateDtoSample(){
        return AccountCreateDto.builder()
                .loginId("kangsy763")
                .password("1qaz2wsx")
                .email("kangsy763@naver.com")
                .nickname("bepoz")
                .name("seungyoon")
                .build();
    }
}