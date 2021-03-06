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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
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
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andDo(document("login-success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("principal").description("아이디"),
                                fieldWithPath("credentials").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("token").type(JsonFieldType.STRING).description("인증 토큰"),
                                fieldWithPath("accountDto.id").type(JsonFieldType.NUMBER).description(" 회원 id"),
                                fieldWithPath("accountDto.name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("accountDto.nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("accountDto.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("accountDto.point").type(JsonFieldType.NUMBER).description("회원 포인트"),
                                fieldWithPath("accountDto.address.city").type(JsonFieldType.STRING).description("회원 상세주소(시)"),
                                fieldWithPath("accountDto.address.street").type(JsonFieldType.STRING).description("회원 상세주소(도로명주소)"),
                                fieldWithPath("accountDto.address.zipcode").type(JsonFieldType.STRING).description("회원 상세주소(우편번호)"),
                                fieldWithPath("accountDto.role").type(JsonFieldType.STRING).description("회원 권한"),
                                fieldWithPath("accountDto.emailCheckToken").type(JsonFieldType.STRING).description("이메일 인증 토큰"),
                                fieldWithPath("accountDto.emailCheckTokenGenDate").type(JsonFieldType.STRING).description("이메일 토큰 발행 시간"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("회원 경로"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                                )));
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
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(unauthenticated())
                .andDo(document("error login-password",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("principal").description("아이디"),
                                fieldWithPath("credentials").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )));
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
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(unauthenticated())
                .andDo(document("error login-id",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("principal").description("아이디"),
                                fieldWithPath("credentials").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )));
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