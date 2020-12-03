package com.ticket.captain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.account.dto.AccountUpdateDto;
import com.ticket.captain.common.Address;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
public class AccountApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public static final String ACCOUNT_EMAIL = "test@email.com";

    public static final Long ERROR_ID = 99L;

    public static Long TEST_ID;

    public static final String API_ACCOUNT_URL = "/api/account/";

    Address address = new Address("seoul", "mapo", "03951");

    @Before
    public void setUp() {

        Account account = Account.builder()
                .email(ACCOUNT_EMAIL)
                .name("test")
                .password(passwordEncoder.encode("1111"))
                .point(5000)
                .address(address)
                .build();

        Account save = accountRepository.save(account);

        TEST_ID = save.getId();
    }

    @After
    public void after() {
        accountRepository.deleteById(TEST_ID);
    }

    @Test
    @DisplayName("사이트 회원들 목록을 page를 추가해 리턴하는 테스트")
    public void 회원_목록() throws Exception {

        Pageable page = PageRequest.of(0, 10);

        mockMvc.perform(get(API_ACCOUNT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(page.getPageNumber()))
                .param("size", String.valueOf(page.getPageSize()))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("list-accounts",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("_embedded.accountDtoList[].id").type(JsonFieldType.NUMBER).description(" 회원 id"),
                                fieldWithPath("_embedded.accountDtoList[].name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("_embedded.accountDtoList[].email").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("_embedded.accountDtoList[].role").type(JsonFieldType.STRING).description("회원 권한"),
                                fieldWithPath("_embedded.accountDtoList[]._links.self.href").type(JsonFieldType.STRING).description("각 회원 경로"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("현재 경로"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로"),
                                fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("한 페이지 의 회원 수"),
                                fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("총 요소"),
                                fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("현재 페이지 인덱스")
                        )
                        )
                );
    }

    @Test
    @DisplayName("한 회원에 대한 정보 출력 테스트")
    public void 회원_상세조회() throws Exception {

        //when
        mockMvc.perform(get(API_ACCOUNT_URL + TEST_ID)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("detail-account",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description(" 회원 id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("role").type(JsonFieldType.STRING).description("회원 권한"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("회원 경로"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )
                        )

                );

    }

    @Test
    @DisplayName("회원 수정 시 값이 정상적으로 보냈는지 테스트")
    public void 회원_수정_성공() throws Exception {

        //given
        AccountUpdateDto updateRequestDto =
                new AccountUpdateDto("update", "update@email.com", "updateNickname");

        mockMvc.perform(MockMvcRequestBuilders.put(API_ACCOUNT_URL + "{id}", TEST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestDto))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("update-account",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("email").description("수정할 이메일"),
                                fieldWithPath("name").description("수정할 이름"),
                                fieldWithPath("nickname").description("수정할 닉네임")
                        ),
                        responseFields(
                                fieldWithPath("id").description("수정된 고유 계정 id"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("회원 경로"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )
                        )
                );
    }

    @Test
    @DisplayName("회원 수정시 수정할 계정이 있는지 확인하는 테스트")
    public void 회원_수정_실패() throws Exception {

        //given
        AccountUpdateDto updateRequestDto =
                new AccountUpdateDto("update", "update@email.com", "updateNickname");

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.put(API_ACCOUNT_URL + ERROR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestDto))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("error-account",
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("message").description("에러 메시지"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )
                        )
                );
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("권한 부여 테스트 성공")
    public void 권한_부여() throws Exception {

        mockMvc.perform(put(API_ACCOUNT_URL + "appoint/{id}", TEST_ID)
                .param("role", Role.ROLE_MANAGER.name())
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("grant-account",
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("수정할 계정 고유 id값")
                        ),
                        requestParameters(
                                parameterWithName("role").description("줄 권한"),
                                parameterWithName("_csrf").ignored()
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("권한이 부여된 고유 계정 id"),
                                fieldWithPath("role").type(JsonFieldType.STRING).description("부여된 계정"),
                                fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("회원 경로"),
                                fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("문서 경로")
                        )
                ));
    }

}