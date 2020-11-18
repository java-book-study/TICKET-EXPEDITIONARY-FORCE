package com.ticket.captain.festival;


import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festivalCategory.FestivalCategory;
import com.ticket.captain.festivalCategory.FestivalCategoryService;
import com.ticket.captain.festivalCategory.dto.FestivalCategoryCreateDto;
import com.ticket.captain.festivalCategory.dto.FestivalCategoryDto;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class FestivalUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private FestivalService festivalService;

    @Autowired
    private FestivalCategoryService festivalCategoryService;

    private Festival festival;

    private FestivalCategory festivalCategory;

    public static final String API_ACCOUNT_URL = "/api/account/festival";

    @BeforeAll
    void beforeAll() {
        FestivalCategoryCreateDto categoryCreateDto = FestivalCategoryCreateDto.builder()
                .categoryName("오페라")
                .createId(1L)
                .build();

        FestivalCategoryDto festivalCategoryDto = festivalCategoryService.add(categoryCreateDto);
        festivalCategory = festivalCategoryService.findByCategoryName(festivalCategoryDto.getCategoryName());

        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("오페라의 유령")
                .content("오페라로 당신을 초대합니다.")
                .createDate(LocalDateTime.now())
                .createId(1L)
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .modifyId(1L)
                .categoryId(festivalCategory.getId())
                .build();

        FestivalDto festivalDto = festivalService.add(createDto);
        festival = festivalService.findByTitle(festivalDto.getTitle());
    }

    @Test
    @Order(1)
    @WithMockUser(value = "mock-user", roles = "USER")
    void festivalInfo() throws Exception {
        mockMvc.perform(get(API_ACCOUNT_URL + "/info/" + festival.getId())
                .param("festivalId", String.valueOf(festival.getId()))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
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
        festivalCategoryService.delete(festivalCategory.getId());
    }
}
