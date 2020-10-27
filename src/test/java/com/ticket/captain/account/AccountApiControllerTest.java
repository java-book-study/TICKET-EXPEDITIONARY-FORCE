package com.ticket.captain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.account.dto.AccountResponseDto;
import com.ticket.captain.account.dto.AccountUpdateRequestDto;
import com.ticket.captain.common.Address;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;
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
    @Before
    public void setUp() {
        Address adrs = new Address("seoul", "mapo", "03951");

        Account account = Account.builder()
                .email(ACCOUNT_EMAIL)
                .name("test")
                .password(passwordEncoder.encode("1111"))
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .point(5000)
                .address(adrs)
                .build();

        Account save = accountRepository.save(account);

        TEST_ID = save.getId();
    }

    @After
    public void after() {
        accountRepository.deleteById(TEST_ID);
    }

    @DisplayName("사이트 회원들 목록을 page를 추가해 리턴하는 테스트")
    @Test
    @Order(1)
    public void 회원목록() throws Exception {

        Pageable page = PageRequest.of(0, 10);

        mockMvc.perform(get(API_ACCOUNT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(page.getPageNumber()))
                .param("size", String.valueOf(page.getPageSize()))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("한 회원에 대한 정보 출력 테스트")
    @Test
    @Order(2)
    public void 회원_상세조회() throws Exception {

        //when
        MvcResult mvcResult = mockMvc.perform(get(API_ACCOUNT_URL + TEST_ID)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        AccountResponseDto accountResponseDto = objectMapper.readValue(contentAsString, AccountResponseDto.class);

        assertEquals(accountService.findAccountDetail(TEST_ID).getEmail(), accountResponseDto.getEmail());

    }

    @DisplayName("회원 수정 시 값이 정상적으로 보냈는지 테스트")
    @Test
    @Order(4)
    public void 회원_수정() throws Exception {

        //given
        AccountUpdateRequestDto updateRequestDto =
                new AccountUpdateRequestDto("update@email.com", "update", Role.ROLE_ADMIN);

        //when
        mockMvc.perform(post(API_ACCOUNT_URL + TEST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestDto))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        Account account = accountRepository.findById(TEST_ID).orElseGet(Account::new);

        //then
        assertEquals("update@email.com", account.getEmail());
        assertEquals("update", account.getName());
//        assertEquals(accountResponseDto.getRole(), Role.ROLE_ADMIN);
    }

    @DisplayName("회원 수정 오류시 값이 변경 되었는지 확인하는 테스트")
    @Test
    @Order(5)
    public void 회원_수정_실패() throws Exception {

        //given
        AccountUpdateRequestDto updateRequestDto =
                new AccountUpdateRequestDto("update@email.com", "update", Role.ROLE_ADMIN);

        //when + then
        mockMvc.perform(post(API_ACCOUNT_URL + ERROR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestDto))
                .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(status().isNotFound());

    }


}