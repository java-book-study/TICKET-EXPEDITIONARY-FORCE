package com.ticket.captain.account;


import com.ticket.captain.account.dto.AccountCreateDto;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AccountLoginControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setUp() throws Exception{
        AccountCreateDto sampleAccount = AccountCreateDto.builder()
                .email("kang")
                .password("kangkang")
                .build();
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void registerAndLoginTest() throws Exception{
        //given
        mockMvc.perform(post("/test/register"))
                .andExpect(jsonPath("email").value("kang"))
                .andDo(print());
        //when

        //then

    }

}