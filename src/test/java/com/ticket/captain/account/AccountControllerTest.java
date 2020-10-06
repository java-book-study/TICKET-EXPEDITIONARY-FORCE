package com.ticket.captain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.mail.EmailMessage;
import com.ticket.captain.mail.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    AccountRepository accountRepository;
    @MockBean
    EmailService emailService;

    @DisplayName("로그인 화면 보이는지 테스트")
    @Test
    public void login_success() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(unauthenticated());

    }

    @DisplayName("회원가입 - 성공")
    @Test
    public void createAccount() throws Exception {
        SignUpForm signUpForm = signUpFormSample();

        Account account = modelMapper.map(signUpForm, Account.class);
        account.setId(10L);
        when(accountRepository.save(account)).thenReturn(account);

        mockMvc.perform(post("/sign-up/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(signUpForm))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(authenticated().withUsername("sonnie"))
                .andExpect(jsonPath("id").exists());

        Account byEmail = accountRepository.findByEmail(signUpForm.getEmail());
        assertNotNull(byEmail);
        assertNotEquals(byEmail.getPassword(), signUpForm.getPassword());
        then(emailService).should().sendEmail(any(EmailMessage.class));
    }

    //TODO : 로그인 유지 되었는지,
    // TODO : session 유지 시간
    // TODO : was 2대에 로그인 잘 되어있는지
    @DisplayName("회원가입 후 이메일 보내기")
    @Test
    void sendEmail_success(){

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