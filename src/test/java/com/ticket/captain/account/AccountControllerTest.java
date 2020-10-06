package com.ticket.captain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.config.SecurityConfig;
import com.ticket.captain.mail.EmailMessage;
import com.ticket.captain.mail.EmailService;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = SecurityConfig.class)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountService accountService;
    @MockBean
    EmailService emailService;

    private Account account = new Account();

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(AccountController.class).build();
        when(modelMapper.map(any(), any())).thenReturn(account);
        account.setId(10L);
    }
    @DisplayName("회원가입 화면 보이는지 테스트")
    @Test
    public void signUpForm_success() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(view().name("account/sign-up"))
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원가입 - 성공")
    @Test
    public void createAccount() throws Exception {
        SignUpForm signUpForm = signUpFormSample();


        Account account = modelMapper.map(signUpForm, Account.class);
        when(modelMapper.map(signUpForm, Account.class)).thenReturn(account);


        when(accountRepository.save(any(Account.class))).thenReturn(account);

        mockMvc.perform(post("/sign-up/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(signUpForm))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(authenticated().withUsername("sonnie"))
                .andExpect(jsonPath("id").exists());

        when(accountRepository.findByEmail(signUpForm.getEmail())).thenReturn(account);
        assertNotNull(account);
        assertNotEquals(account.getPassword(), signUpForm.getPassword());
        then(emailService).should().sendEmail(any(EmailMessage.class));
    }

    @DisplayName("인증 메일 확인 - 입력값 정상")
    @Test
    void checkEmailToken_success() throws Exception {
        SignUpForm signUpForm = signUpFormSample();
        Account newAccount = modelMapper.map(signUpForm, Account.class);
        newAccount.setId(10L);
        newAccount.setPassword(passwordEncoder.encode(newAccount.getPassword()));
        when(accountRepository.save(any(Account.class))).thenReturn(newAccount);
        newAccount.generateEmailCheckToken();

        mockMvc.perform(get("/check-email-token")
                .param("token", newAccount.getEmailCheckToken())
                .param("email", newAccount.getEmail()))
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername("sonnie"))
                .andExpect(jsonPath("login").exists());

        assertTrue(newAccount.isEmailVerified());
    }

    //TODO: 이메일 account not found
    //TODO: !isValidToken 인 경우 작성

    //TODO : 로그인 유지 되었는지,
    // TODO : was 2대에 로그인 잘 되어있는지
    @DisplayName("header에 로그인 token 저장 성공")
    @Test
    void check_login_token_in_Header_success(){

    }



    private SignUpForm signUpFormSample(){
        return SignUpForm.builder()
                .email("sonnie@email.com")
                .loginId("shahn")
                .nickname("sonnie")
                .name("안소현")
                .password("1qaz2wsx")
                .build();
    }

}