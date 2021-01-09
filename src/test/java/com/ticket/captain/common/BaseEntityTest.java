package com.ticket.captain.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseEntityTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private FestivalService festivalService;

    @Autowired
    private ObjectMapper objectMapper;

    private Festival festival;

    public static final String API_MANAGER_URL = "/api/manager/festival";

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    void generateFestival() throws Exception {
        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("Rock Festival")
                .content("Come and Join Us")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.name())
                .build();

        mockMvc.perform(post(API_MANAGER_URL + "/generate/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.title").value("Rock Festival"))
                .andExpect(jsonPath("data.content").value("Come and Join Us"))
                .andExpect(jsonPath("data.festivalCategory").value("ROCK"))
                .andDo(print())
        ;
    }
}