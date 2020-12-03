package com.ticket.captain.festival;


import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class FestivalUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private FestivalService festivalService;

    private Festival festival;

    public static final String API_ACCOUNT_URL = "/api/account/festival";

    @BeforeAll
    void beforeAll() {
        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("Rock Festival")
                .content("Come and Join Us")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.toString())
                .build();

        FestivalDto festivalDto = festivalService.add(createDto);
        festival = festivalService.findByTitle(festivalDto.getTitle());
    }

    @Test
    @WithMockUser(value = "mock-user", roles = "USER")
    void festivalInfo() throws Exception {
        mockMvc.perform(get(API_ACCOUNT_URL + "/info/" + festival.getId())
                .param("festivalId", String.valueOf(festival.getId()))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "mock-user", roles = "USER")
    void festivals() throws Exception {
        mockMvc.perform(get(API_ACCOUNT_URL +"/festivals")
                .param("offset", String.valueOf(0))
                .param("limit", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @AfterAll
    void afterAll() {
        festivalService.delete(festival.getId());
    }
}
