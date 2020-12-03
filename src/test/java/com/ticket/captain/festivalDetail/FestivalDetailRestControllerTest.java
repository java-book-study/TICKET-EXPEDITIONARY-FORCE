package com.ticket.captain.festivalDetail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.enumType.SalesType;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festival.dto.FestivalDto;
import com.ticket.captain.festivalDetail.dto.FestivalDetailCreateDto;
import com.ticket.captain.festivalDetail.dto.FestivalDetailUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class FestivalDetailRestControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private FestivalDetailRepository festivalDetailRepository;

    public Festival savedFestival;

    public FestivalDetail savedFestivalDetail;

    public static final String API_MANAGER_URL = "/api/manager/festivalDetail";

    @BeforeEach
    void beforeAll() {
        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("DPR LIVE")
                .content("IAOT LIVE")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.toString())
                .build();

        savedFestival = festivalRepository.save(createDto.toEntity());

        FestivalDetailCreateDto festivalDetailCreateDto = FestivalDetailCreateDto.builder()
                .salesType(SalesType.FREE.toString())
                .amount(10000L)
                .price(20000L)
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .build();

        savedFestivalDetail = festivalDetailRepository.save(festivalDetailCreateDto.toEntity());
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @DisplayName("FestivalDetail Create 테스트")
    public void generateTest() throws Exception{
        //when
        FestivalDetailCreateDto festivalDetailCreateDto = FestivalDetailCreateDto.builder()
                .salesType(SalesType.FREE.toString())
                .amount(1000L)
                .price(2000L)
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .build();

        //then
        mockMvc.perform(post(API_MANAGER_URL + "/generate/" + savedFestival.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(festivalDetailCreateDto))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.amount").value(1000L))
                .andExpect(jsonPath("data.price").value(2000L))
                .andExpect(jsonPath("data.salesType").value("FREE"))
                .andDo(print())
        ;
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @DisplayName("FestivalDetail 조회 테스트")
    public void infoTest() throws Exception{
        mockMvc.perform(get(API_MANAGER_URL + "/info/" + savedFestivalDetail.getId())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.amount").value(10000L))
                .andExpect(jsonPath("data.price").value(20000L))
                .andExpect(jsonPath("data.salesType").value("FREE"))
                .andDo(print())
        ;
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    @DisplayName("FestivalDetail Update 테스트")
    public void updateTest() throws Exception{
        //given
        FestivalDetailUpdateDto festivalDetailUpdateDto = FestivalDetailUpdateDto.builder()
                .amount(300L)
                .price(500L)
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .salesType(SalesType.DRAW.toString())
                .build();
        //when&then
        mockMvc.perform(put(API_MANAGER_URL + "/update/" + savedFestivalDetail.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(festivalDetailUpdateDto))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.amount").value(300L))
                .andExpect(jsonPath("data.price").value(500L))
                .andExpect(jsonPath("data.salesType").value("DRAW"))
                .andDo(print())
        ;
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @DisplayName("FestivalDetail Delete 테스트")
    public void deleteTest() throws Exception{
        //given
        mockMvc.perform(delete(API_MANAGER_URL + "/delete/" + savedFestivalDetail.getId())
                .with(csrf()))
                .andExpect(status().is(200))
                .andDo(print());
    }

}