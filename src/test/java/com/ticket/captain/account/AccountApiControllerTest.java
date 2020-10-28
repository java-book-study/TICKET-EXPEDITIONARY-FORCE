package com.ticket.captain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.account.dto.AccountResponseDto;
import com.ticket.captain.account.dto.AccountUpdateRequestDto;
import com.ticket.captain.common.Address;
import org.assertj.core.api.Assertions;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

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

    public static final Long errId = -1L;

    public static Long testId;

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

        testId = save.getId();
    }

    @After
    public void after() {
        accountRepository.deleteById(testId);
    }

    @DisplayName("사이트 회원들 목록을 page를 추가해 리턴하는 테스트")
    @Test
    @Order(1)
    public void 회원목록() throws Exception {

        Pageable page = PageRequest.of(0, 10);
        String AccountListAsString = objectMapper.writeValueAsString(accountService.findAccountList(page));

        MvcResult mvcResult = mockMvc.perform(get("/api/account")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(page.getPageNumber()))
                .param("size", String.valueOf(page.getPageSize()))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(AccountListAsString))
                .andReturn();
    }

    @DisplayName("한 회원에 대한 정보 출력 테스트")
    @Test
    @Order(2)
    public void 회원_상세조회() throws Exception {

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api/account/" + testId)
                                                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        AccountResponseDto accountResponseDto = objectMapper.readValue(contentAsString, AccountResponseDto.class);

        assertEquals(accountService.findAccountDetail(testId).getEmail(), accountResponseDto.getEmail());

    }

    @DisplayName("한 회원 대한 응답이 정상이 아닌 경우 테스트")
    @Test
    @Order(3)
    public void 회원_상세조회_실패() throws Exception {

        //when
        mockMvc.perform(get("/api/account/" + errId)
                .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("회원 수정 시 값이 정상적으로 보냈는지 테스트")
    @Test
    @Order(4)
    public void 회원_수정() throws Exception {

        //given
        AccountUpdateRequestDto updateRequestDto =
                new AccountUpdateRequestDto("update@email.com", "update", Role.ROLE_ADMIN);

        //when
        mockMvc.perform(post("/api/account/" + testId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestDto))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        AccountResponseDto accountResponseDto = accountService.findAccountDetail(testId);

        //then
        assertEquals(accountResponseDto.getEmail(), "update@email.com");
        assertEquals(accountResponseDto.getName(), "update");
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
        mockMvc.perform(post("/api/account/" + errId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestDto))
                .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(status().isBadRequest());

    }

    @DisplayName("기본 유저는 ROLE_USER를 가지고 있는지 테스트")
    @Test
    public void containsUSERTest() throws Exception{
        //given
        Address adrs = new Address("seoul", "mapo", "03951");

        Account adminAccount = Account.builder()
                .email("admin@gmail.com")
                .name("adminTest")
                .password(passwordEncoder.encode("1111"))
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .point(5000)
                .address(adrs)
                .build();
        Account savedAccount = accountRepository.save(adminAccount);
        //then
        assertThat(savedAccount.getRole()).isEqualTo(Role.ROLE_USER);
    }

    @DisplayName("addRole메서드 테스트")
    @Test
    public void addRoleTEst() throws Exception{
        //given
        Address adrs = new Address("seoul", "mapo", "03951");

        Account adminAccount = Account.builder()
                .email("admin@gmail.com")
                .name("adminTest")
                .password(passwordEncoder.encode("1111"))
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .point(5000)
                .address(adrs)
                .build();
        Account savedAccount = accountRepository.save(adminAccount);
        //when
        savedAccount.addRole(Role.ROLE_MANAGER);
        //then
        assertThat(savedAccount.getRole()).isNotEqualTo(Role.ROLE_USER);
        assertThat(savedAccount.getRole()).isEqualTo(Role.ROLE_MANAGER);
    }

    @DisplayName("managerAppoint 메서드를 통한 ROLE 변경 확인 테스트")
    @Test
    public void roleUpdateTest() throws Exception{
        //given
        Address adrs = new Address("seoul", "mapo", "03951");

        Account adminAccount = Account.builder()
                .email("admin@gmail.com")
                .name("adminTest")
                .password(passwordEncoder.encode("1111"))
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .point(5000)
                .address(adrs)
                .build();
        Account savedAccount = accountRepository.save(adminAccount);
        Long id = savedAccount.getId();
        //when
        assertThat(savedAccount.getRole()).isEqualTo(Role.ROLE_USER);
        accountService.managerAppoint(id);
        Account findAccount = accountRepository.findById(id).orElseThrow(NullPointerException::new);
        //then
        assertThat(findAccount.getRole()).isEqualTo(Role.ROLE_MANAGER);
    }

    @DisplayName("managerAppoint API 테스트")
    //이거하면 권한가지고 테스트 가능
    @WithMockUser("ROLE_ADMIN")
    @Test
    public void appointApiTest() throws Exception{
        //given
        Address adrs = new Address("seoul", "mapo", "03951");
        //when

        //then
        mockMvc.perform(put("/api/appoint/"+testId)
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.role").value("ROLE_MANAGER"));
    }
}