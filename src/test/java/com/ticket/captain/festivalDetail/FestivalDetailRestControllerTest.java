package com.ticket.captain.festivalDetail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.enumType.SalesType;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festivalDetail.dto.FestivalDetailCreateDto;
import com.ticket.captain.festivalDetail.dto.FestivalDetailUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class FestivalDetailRestControllerTest {

    public static final String API_MANAGER_URL = "/api/manager/festivalDetail/";
    public Festival savedFestival;
    public FestivalDetail savedFestivalDetail;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FestivalService festivalService;
    @Autowired
    private FestivalDetailService festivalDetailService;
    @Autowired
    private FestivalRepository festivalRepository;
    @Autowired
    private FestivalDetailRepository festivalDetailRepository;

    @BeforeEach
    void beforeAll() {
        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("DPR LIVE")
                .content("IAOT LIVE")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.name())
                .build();

        savedFestival = festivalRepository.save(createDto.toEntity());

        FestivalDetailCreateDto festivalDetailCreateDto = FestivalDetailCreateDto.builder()
                .salesType(SalesType.FREE.toString())
                .amount(10000L)
                .price(BigDecimal.valueOf(20000))
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .festival(savedFestival)
                .build();

        savedFestivalDetail = festivalDetailRepository.save(festivalDetailCreateDto.toEntity());
    }

    @AfterEach
    void afterAll() {
        festivalDetailRepository.deleteById(savedFestivalDetail.getId());
        festivalRepository.deleteById(savedFestival.getId());
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @DisplayName("FestivalDetail Create 테스트")
    public void generateTest() throws Exception {
        //when
        FestivalDetailCreateDto festivalDetailCreateDto = FestivalDetailCreateDto.builder()
                .salesType(SalesType.FREE.toString())
                .amount(1000L)
                .price(BigDecimal.valueOf(2000))
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .festival(savedFestival)
                .build();

        //then
        MvcResult result = mockMvc.perform(post(API_MANAGER_URL + savedFestival.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(festivalDetailCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.amount").value(1000L))
                .andExpect(jsonPath("data.price").value(2000L))
                .andExpect(jsonPath("data.salesType").value("FREE"))
                .andDo(print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Map response = objectMapper.readValue(content, Map.class);
        LinkedHashMap data = (LinkedHashMap) response.get("data");
        Integer id = (Integer) data.get("id");

        festivalDetailRepository.deleteById(id.longValue());
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @DisplayName("FestivalDetail 조회 테스트")
    public void infoTest() throws Exception {
        mockMvc.perform(get(API_MANAGER_URL + savedFestivalDetail.getId()))
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
    public void updateTest() throws Exception {
        //given
        FestivalDetailUpdateDto festivalDetailUpdateDto = FestivalDetailUpdateDto.builder()
                .amount(300L)
                .price(BigDecimal.valueOf(500L))
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .salesType(SalesType.DRAW.name())
                .build();
        //when&then
        mockMvc.perform(put(API_MANAGER_URL + savedFestivalDetail.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(festivalDetailUpdateDto)))
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
    public void deleteTest() throws Exception {
        //given
        FestivalDetailCreateDto festivalDetailCreateDto = FestivalDetailCreateDto.builder()
                .salesType(SalesType.FREE.toString())
                .amount(1000L)
                .price(BigDecimal.valueOf(2000))
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .festival(savedFestival)
                .build();

        FestivalDetail festivalDetail = festivalDetailRepository.save(festivalDetailCreateDto.toEntity());

        mockMvc.perform(delete(API_MANAGER_URL + festivalDetail.getId()))
                .andExpect(status().is(200))
                .andDo(print());
    }

}