package com.ticket.captain.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.enumType.FestivalCategory;
import com.ticket.captain.enumType.SalesType;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.festival.dto.FestivalCreateDto;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.festivalDetail.FestivalDetailRepository;
import com.ticket.captain.festivalDetail.dto.FestivalDetailCreateDto;
import com.ticket.captain.ticket.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class OrderServiceTest {
    public static final String API_ORDER_URL = "/api/order/";
    public static final String ACCOUNT_EMAIL = "testEmail@naver.com";

    public Festival savedFestival;
    public FestivalDetail savedFestivalDetail;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private FestivalDetailRepository festivalDetailRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FestivalRepository festivalRepository;

    @BeforeEach
    @WithMockUser(value = ACCOUNT_EMAIL, roles = "MANAGER")
    void beforeAll() {
        FestivalCreateDto createDto = FestivalCreateDto.builder()
                .title("ORDER FESTIVAL")
                .content("FESORD")
                .salesStartDate(LocalDateTime.now())
                .salesEndDate(LocalDateTime.now())
                .festivalCategory(FestivalCategory.ROCK.toString())
                .build();

        savedFestival = festivalRepository.save(createDto.toEntity());

        FestivalDetailCreateDto festivalDetailCreateDto = FestivalDetailCreateDto.builder()
                .salesType(SalesType.FREE.toString())
                .amount(30000L)
                .price(BigDecimal.valueOf(30000))
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .build();

        FestivalDetail festivalDetail = festivalDetailCreateDto.toEntity();
        festivalDetail.setFestival(savedFestival);
        savedFestivalDetail = festivalDetailRepository.save(festivalDetail);
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @DisplayName("Order 생성 확인")
    public void orderServiceTest() throws Exception {
        mockMvc.perform(post(API_ORDER_URL + savedFestivalDetail.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        ;
    }
}