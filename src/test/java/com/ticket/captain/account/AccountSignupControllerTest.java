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
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
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
class AccountSignupControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @MockBean
    private EmailService emailService;

    @DisplayName("회원가입 - 입력값 정상")
    @Test
    public void createAccount_correct_input() throws Exception {
        AccountCreateDto accountCreateDto = accountCreateDtoSample();

        MvcResult mvcResult = mockMvc.perform(post("/api/sign-up")
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(accountCreateDto)))
                .andExpect(status().isOk())
                .andDo(document("signUp-success",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("아이디"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("address.city").description("상세주소(시)"),
                                fieldWithPath("address.street").description("상세주소(도로명 주소)"),
                                fieldWithPath("address.zipcode").description("상세주소(우편번호)")
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
                        )))
                .andDo(print())
                .andReturn();

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
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(accountCreateDto)))
                .andDo(document("signUp-fail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("아이디"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("address.city").description("상세주소(시)"),
                                fieldWithPath("address.street").description("상세주소(도로명 주소)"),
                                fieldWithPath("address.zipcode").description("상세주소(우편번호)")
                        ),
                        responseFields(
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )))
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
                .contentType(MediaTypes.HAL_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(accountCreateDto2)))
                .andDo(print())
                .andExpect(status().is(400))
                .andDo(document("email-validate-fail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )))
                .andDo(print());
    }

    @DisplayName("인증 메일 확인 - 입력값 정상")
    @Test
    void checkEmailToken_success() throws Exception {
        Account newAccount = accountSample();

        MvcResult mvcResult = mockMvc.perform(get("/api/check-email-token")
                .param("token", newAccount.getEmailCheckToken())
                .param("email", newAccount.getEmail()))
                .andExpect(status().isOk())
                .andExpect(unauthenticated())
                .andDo(document("email-auth-success",
                        getDocumentRequest(),
                        getDocumentResponse(),

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
                        )))
                .andDo(print())
                .andReturn();

    }

    @DisplayName("인증 메일 확인 - 계정 존재하지 않을 때")
    @Test
    void checkEmailToken_with_account_null() throws Exception {

        Account account = accountSample();

        MvcResult mvcResult = mockMvc.perform(get("/api/check-email-token")
                .param("token", account.getEmailCheckToken())
                .param("email", "email@email.com"))
                .andExpect(unauthenticated())
                .andExpect(status().is(400))
                .andDo(document("email-auth-fail",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )))
                .andDo(print())
                .andReturn();

    }

    @DisplayName("인증 메일 확인 - 토큰값 오류")
    @Test
    void checkEmailToken_with_wrong_input() throws Exception {

        Account account = accountSample();
        MvcResult mvcResult = mockMvc.perform(get("/api/check-email-token")
                .param("token", UUID.randomUUID().toString())
                .param("email", account.getEmail()))
                .andExpect(status().is(401))
                .andDo(document("token-auth-fail",
                        getDocumentRequest(),
                        getDocumentResponse(),

                        responseFields(
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )))
                .andDo(print())
                .andReturn();
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