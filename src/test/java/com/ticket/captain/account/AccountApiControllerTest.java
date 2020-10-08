package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountResponseDto;
import com.ticket.captain.account.dto.AccountUpdateRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class AccountApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @SpyBean(name = "objectMapper")
    private ObjectMapper objectMapper;

    @Before
    public void setUp(){
        Account account = Account.builder()
                .email("test@email.com")
                .name("test")
                .password(passwordEncoder.encode("1111"))
                .phone("010-9981-3056")
                .role(Role.ROLE_USER)
                .address(new Address("seoul", "mapo", "03951"))
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .build();

        accountRepository.save(account);
    }

    @Test
    public void 회원목록() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/account")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        String pageString = objectMapper.writeValueAsString(accountService.findAccountList(PageRequest.of(0, 10)));
        assertEquals(contentAsString, pageString);
    }

    @Test
    public void 회원_상세조회() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/account/" + 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        AccountResponseDto accountResponseDto = objectMapper.readValue(contentAsString, AccountResponseDto.class);

        assertEquals(accountService.findAccountDetail(1L).getEmail(), accountResponseDto.getEmail());

    }

    @Test
    public void 회원_수정() throws Exception {

        AccountUpdateRequestDto updateRequestDto =
                new AccountUpdateRequestDto("update@email.com", "update", Role.ROLE_ADMIN);

        //when
        mockMvc.perform(post("/api/account/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestDto))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        AccountResponseDto accountResponseDto = accountService.findAccountDetail(1L);

        //then
        assertEquals(accountResponseDto.getEmail(), "update@email.com");
        assertEquals(accountResponseDto.getName(), "update");
        assertEquals(accountResponseDto.getRole(), Role.ROLE_ADMIN);
    }

}