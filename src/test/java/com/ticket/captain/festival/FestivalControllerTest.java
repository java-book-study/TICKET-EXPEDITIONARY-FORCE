package com.ticket.captain.festival;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festival.dto.FestivalUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class FestivalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private FestivalService festivalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Festival festival;

    public static final String API_MANAGER_URL = "/api/manager/festival";

    @BeforeEach
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
        log.info("beforeEach end");
    }

    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @Test
    public void validatorTest() throws Exception{
        //given
        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("Rock Festival")
                .content("Come and Join Us")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.toString())
                .build();

        //then
        mockMvc.perform(post(API_MANAGER_URL + "/generate/"+2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto))
                .with(csrf()))
                .andDo(print())
            ;
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void generateFestival() throws Exception {
        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("Generate Festival")
                .content("Come and Join Us")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.toString())
                .build();

        mockMvc.perform(post(API_MANAGER_URL + "/generate/"+2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value("Generate Festival"))
                .andExpect(jsonPath("data.content").value("Come and Join Us"))
                .andExpect(jsonPath("data.festivalCategory").value("ROCK"))
                .andDo(print())
        ;
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void festivalInfo() throws Exception {
        mockMvc.perform(get(API_MANAGER_URL + "/info/" + festival.getId())
                .with(csrf()))
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
        mockMvc.perform(get(API_MANAGER_URL +"/festivals")
                .param("offset", String.valueOf(0))
                .param("limit", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void updateFestival() throws Exception {

        FestivalUpdateDto updateDto = FestivalUpdateDto.builder()
                .title("Charity Concert")
                .content("Enjoy And Donate")
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.CHARITY.toString())
                .build();

        mockMvc.perform(put(API_MANAGER_URL + "/update/" + festival.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value("Charity Concert"))
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
                .festivalCategory(FestivalCategory.ROCK.toString())
                .build();

        FestivalDto festivalDto = festivalService.add(createDto);
        Festival findFestival = festivalService.findByTitle(festivalDto.getTitle());

        mockMvc.perform(delete(API_MANAGER_URL + "/delete/" + findFestival.getId())
                .with(csrf()))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @AfterEach
    void afterAll() {
        log.info("afterAll started");
        festivalService.delete(festival.getId());
        log.info("afterAll ended");
    }

    private Class<? extends Exception> getApiResultExceptionClass(MvcResult result) {
        return Objects.requireNonNull(result.getResolvedException().getClass());
    }
}
