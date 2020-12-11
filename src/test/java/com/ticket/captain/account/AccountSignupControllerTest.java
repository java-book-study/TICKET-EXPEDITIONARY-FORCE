package com.ticket.captain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.common.Address;
import com.ticket.captain.exception.UnauthorizedException;
import com.ticket.captain.mail.EmailMessage;
import com.ticket.captain.mail.EmailService;
import com.ticket.captain.response.ApiResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureRestDocs
//@ContextConfiguration(classes = SecurityConfig.class)
class AccountSignupControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    @MockBean
    EmailService emailService;

    @DisplayName("회원가입 - 입력값 정상")
    @Test
    public void createAccount_correct_input() throws Exception {
        AccountCreateDto accountCreateDto = accountCreateDtoSample();

        MvcResult mvcResult = mockMvc.perform(post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(accountCreateDto)))
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ApiResponseDto<?> apiResponseDto = new ObjectMapper().readValue(contentAsString, ApiResponseDto.class);

        assertEquals(200, apiResponseDto.getCode().getHttpStatus());

        then(emailService).should().sendEmail(any(EmailMessage.class));
    }

    @DisplayName("회원가입 - 입력값 오류")
    @Test
    public void createAccount_wrong_input() throws Exception {
        Address address = new Address("seoul", "gangnam", "111");

        AccountCreateDto accountCreateDto = AccountCreateDto.builder()
                .email("email..")
                .name("sonnie")
                .password("111")
                .nickname("eeee")
                .address(address)
                .build();

        mockMvc.perform(post("/api/sign-up/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(accountCreateDto)))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @DisplayName("이미 가입되어있는 이메일로 가입 시 validate 처리가 잘 되는지")
    @Test
    public void createAccount_sameEmail() throws Exception {
        //given
        AccountCreateDto accountCreateDto = accountCreateDtoSample();
        accountSample();
        //then
        AccountCreateDto accountCreateDto2 = accountCreateDto;
        //when&then
        mockMvc.perform(post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(accountCreateDto2)))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @DisplayName("인증 메일 확인 - 입력값 정상")
    @Test
    void checkEmailToken_success() throws Exception {
        Account newAccount = accountSample();

        MvcResult mvcResult = mockMvc.perform(get("/api/check-email-token")
                .param("token", newAccount.getEmailCheckToken())
                .param("email", newAccount.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ApiResponseDto<?> apiResponseDto = new ObjectMapper().readValue(contentAsString, ApiResponseDto.class);

        assertEquals(200, apiResponseDto.getCode().getHttpStatus());


    }

    @DisplayName("인증 메일 확인 - 계정 존재하지 않을 때")
    @Test
    void checkEmailToken_with_account_null() throws Exception {

        Account account = accountSample();

        MvcResult mvcResult = mockMvc.perform(get("/api/check-email-token")
                .param("token", account.getEmailCheckToken())
                .param("email", "email@email.com"))
                .andExpect(unauthenticated())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ApiResponseDto<?> apiResponseDto = new ObjectMapper().readValue(contentAsString, ApiResponseDto.class);

        assertEquals(401, apiResponseDto.getCode().getHttpStatus());
    }

    @DisplayName("인증 메일 확인 - 토큰값 오류")
    @Test
    void checkEmailToken_with_wrong_input() throws Exception {

        Account account = accountSample();
        MvcResult mvcResult = mockMvc.perform(get("/api/check-email-token")
                .param("token", UUID.randomUUID().toString())
                .param("email", account.getEmail()))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ApiResponseDto<?> apiResponseDto = new ObjectMapper().readValue(contentAsString, ApiResponseDto.class);

        assertEquals(401, apiResponseDto.getCode().getHttpStatus());
    }


    private AccountCreateDto accountCreateDtoSample() {
        Address address = new Address("seoul", "gangnam", "111");

        return AccountCreateDto.builder()
                .email("modunaeggu@naver.com")
                .password("1qaz2wsx")
                .nickname("sonnie")
                .name("sohyun")
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