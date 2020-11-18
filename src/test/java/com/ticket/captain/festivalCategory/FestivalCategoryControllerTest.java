package com.ticket.captain.festivalCategory;

import com.ticket.captain.festivalCategory.dto.FestivalCategoryCreateDto;
import com.ticket.captain.festivalCategory.dto.FestivalCategoryDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class FestivalCategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private FestivalCategoryService festivalCategoryService;

    private FestivalCategory festivalCategory;

    private FestivalCategoryCreateDto categoryCreateDto;

    public static final String API_CATEGORY_URL = "/api/manager/festivalCategory";


    @BeforeAll
    void beforAll() {
        categoryCreateDto = FestivalCategoryCreateDto.builder()
                .categoryName("뮤지컬")
                .createDate(LocalDateTime.now())
                .createId(1L)
                .modifyDate(LocalDateTime.now())
                .modifyId(1L)
                .build();

        festivalCategoryService.add(categoryCreateDto);
        festivalCategory = festivalCategoryService.findByCategoryName("뮤지컬");

    }


    @Test
    @Order(1)
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void categoryInfo() throws Exception{
        mockMvc.perform(get(API_CATEGORY_URL + "/info/" + festivalCategory.getId())
                        .param("festivalCategoryId", String.valueOf(festivalCategory.getId()))
                        .with(csrf()))
                        .andDo(print())
                        .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void categories() throws Exception{
        mockMvc.perform(get(API_CATEGORY_URL + "/festivalCategories")
                .param("offset", "0")
                .param("limit", "1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void deleteCategory() throws Exception {

        mockMvc.perform(delete( API_CATEGORY_URL +"/delete/" + festivalCategory.getId())
                .param("festivalCategory", String.valueOf(festivalCategory.getId()))
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @AfterAll
    void afterAll() {
        festivalCategoryService.delete(festivalCategory.getId());
    }
}
