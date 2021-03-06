package com.ticket.captain.festival;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festival.dto.FestivalUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class FestivalControllerTest {

    public static final String API_MANAGER_URL = "/api/manager/festival/";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private FestivalService festivalService;
    @Autowired
    private ObjectMapper objectMapper;
    private FestivalDto festivalDto;
    private FestivalCreateDto createDto;

    @BeforeEach
    void beforeAll() {
        createDto = FestivalCreateDto.builder()
                .title("Rock Festival")
                .content("Come and Join Us")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.name())
                .build();

        festivalDto = festivalService.add(createDto);
        log.info("beforeEach end");
    }

    @AfterEach
    void afterAll() {
        festivalService.delete(festivalDto.getId());
    }

    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @Test
    public void validatorTest() throws Exception {
        //then
        mockMvc.perform(post(API_MANAGER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(jsonPath("code").value("VALIDATION_ERROR"))
                .andDo(print());
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void generateFestival() throws Exception {
        FestivalCreateDto festivalCreateDto = FestivalCreateDto.builder()
                .title("Generate Festival")
                .content("Come and Join Us")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.name())
                .build();

        MvcResult result = mockMvc.perform(post(API_MANAGER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(festivalCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value("Generate Festival"))
                .andExpect(jsonPath("data.content").value("Come and Join Us"))
                .andExpect(jsonPath("data.festivalCategory").value("ROCK"))
                .andDo(print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        LinkedHashMap data = (LinkedHashMap) response.get("data");
        Integer id = (Integer) data.get("id");

        festivalService.delete(id.longValue());
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void festivalInfo() throws Exception {
        mockMvc.perform(get(API_MANAGER_URL + festivalDto.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value("Rock Festival"))
                .andExpect(jsonPath("data.content").value("Come and Join Us"))
                .andExpect(jsonPath("data.festivalCategory").value("ROCK"))
        ;
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void festivals() throws Exception {
        mockMvc.perform(get(API_MANAGER_URL)
                .param("offset", String.valueOf(0))
                .param("limit", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void updateFestival() throws Exception {
        FestivalUpdateDto updateDto = FestivalUpdateDto.builder()
                .title("Updated Festival")
                .content("Enjoy And Donate")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.CHARITY.name())
                .build();

        mockMvc.perform(put(API_MANAGER_URL + festivalDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value("Updated Festival"))
                .andExpect(jsonPath("data.content").value("Enjoy And Donate"))
                .andExpect(jsonPath("data.festivalCategory").value("CHARITY"))
                .andDo(print());

    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void deleteFestival() throws Exception {
        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("Delete Festival")
                .content("Come and Join Us")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.name())
                .build();

        FestivalDto festivalDto = festivalService.add(createDto);
        Festival findFestival = festivalService.findByTitle(festivalDto.getTitle());

        mockMvc.perform(delete(API_MANAGER_URL + findFestival.getId()))
                .andExpect(status().is(200))
                .andDo(print());
    }

    private Class<? extends Exception> getApiResultExceptionClass(MvcResult result) {
        return Objects.requireNonNull(result.getResolvedException().getClass());
    }
}
