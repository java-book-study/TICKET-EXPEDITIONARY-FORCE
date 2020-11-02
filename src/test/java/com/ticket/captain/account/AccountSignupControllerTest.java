package com.ticket.captain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.mail.EmailMessage;
import com.ticket.captain.mail.EmailService;
import com.ticket.captain.response.ApiResponseCode;
import com.ticket.captain.response.ApiResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring5.expression.Mvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO: 단위 테스트로 바꾸기
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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

    @DisplayName("회원가입 화면 보이는지 테스트")
    @Test
    public void signUpForm_success() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(view().name("account/sign-up"))
                .andExpect(model().attributeExists("accountCreateDto"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원가입 - 입력값 정상")
    @Test
    public void createAccount_correct_input() throws Exception {
        AccountCreateDto accountCreateDto = accountCreateDtoSample();

        mockMvc.perform(post("/sign-up/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(accountCreateDto))
                .with(csrf()))
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
        AccountCreateDto accountCreateDto = AccountCreateDto.builder()
                    .name("sonnie")
                    .password("111")
                    .email("email..")
                    .nickname("eeee")
                    .loginId("shahn2")
                    .build();

        MvcResult mvcResult = mockMvc.perform(post("/api/sign-up/")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(new ObjectMapper().writeValueAsString(accountCreateDto))
                                .with(csrf()))
                                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ApiResponseDto<?> apiResponseDto = new ObjectMapper().readValue(contentAsString, ApiResponseDto.class);

        assertEquals(400, apiResponseDto.getCode().getHttpStatus());
    }

    @DisplayName("이미 가입되어있는 이메일로 가입 시 validate 처리가 잘 되는지")
    @Test
    public void createAccount_sameEmail() throws Exception{
        //given
        AccountCreateDto accountCreateDto = accountCreateDtoSample();
        accountService.createAccount(accountCreateDto);
        //then
        AccountCreateDto accountCreateDto2 = accountCreateDtoSample();
        //when&then
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(accountCreateDto2))
                .with(csrf()))
                .andDo(print());
    }

    @DisplayName("인증 메일 확인 - 입력값 정상")
    @Test
    void checkEmailToken_success() throws Exception {
        Account newAccount = accountSample();

        mockMvc.perform(get("/sign-up/check-email-token")
                .param("token", newAccount.getEmailCheckToken())
                .param("email", newAccount.getEmail()))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(unauthenticated());


        assertTrue(newAccount.isEmailVerified());
    }

    @DisplayName("인증 메일 확인 - 입력값 오류")
    @Test
    void checkEmailToken_with_wrong_input() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/sign-up/check-email-token")
                                .param("token", "1qaz")
                                .param("email", "email@email.com"))
                                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ApiResponseDto<?> apiResponseDto = new ObjectMapper().readValue(contentAsString, ApiResponseDto.class);

        assertEquals(400, apiResponseDto.getCode().getHttpStatus());
    }

    //TODO : 로그인 유지 되었는지,
    // TODO : was 2대에 로그인 잘 되어있는지
    @DisplayName("header에 로그인 token 저장 성공")
    @Test
    void check_login_token_in_Header_success(){

    }

    private AccountCreateDto accountCreateDtoSample(){
        return AccountCreateDto.builder()
                .loginId("shahn2")
                .password("1qaz2wsx")
                .email("modunaeggu@naver.com")
                .nickname("sonnie")
                .name("sohyun")
                .build();
    }

    private Account accountSample(){
        Account account = Account.builder()
                .loginId("sonnie1")
                .email("sonnie@email.com")
                .password("1qaz2wsx")
                .nickname("sonnie")
                .build();
        Account newAccount = accountRepository.save(account);
        newAccount.generateEmailCheckToken();
        return newAccount;
    }
}