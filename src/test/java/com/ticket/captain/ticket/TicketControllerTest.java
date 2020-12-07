package com.ticket.captain.ticket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.enumType.SalesType;
import com.ticket.captain.enumType.StatusCode;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.festivalDetail.FestivalDetailRepository;
import com.ticket.captain.festivalDetail.dto.FestivalDetailCreateDto;
import com.ticket.captain.ticket.dto.TicketCreateDto;
import com.ticket.captain.ticket.dto.TicketUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FestivalDetailRepository festivalDetailRepository;
    @Autowired
    private FestivalRepository festivalRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public static final String API_TICKET_URL = "/api/ticket/";
    public FestivalDetail savedFestivalDetail;
    public Ticket savedTicket;

    @BeforeEach
    void BeforeEach(){
        FestivalDetailCreateDto festivalDetailCreateDto = FestivalDetailCreateDto.builder()
                .salesType(SalesType.FREE.toString())
                .amount(10000L)
                .price(20000L)
                .drawDate(LocalDateTime.now())
                .processDate(LocalDateTime.now())
                .build();
        savedFestivalDetail = festivalDetailRepository.save(festivalDetailCreateDto.toEntity());

        TicketCreateDto ticketCreateDto = TicketCreateDto.builder()
                .ticketNo("ticketNo-001")
                .price(10000L)
                .statusCode(StatusCode.DELIVERED.name())
                .build();
        Ticket ticket = ticketCreateDto.toEntity();
        ticket.festivalDetailSetting(savedFestivalDetail);
        savedTicket = ticketRepository.save(ticket);
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @DisplayName("티켓 생성 테스트")
    public void ticketAddTest() throws Exception{
        //given
        TicketCreateDto ticketCreateDto = TicketCreateDto.builder()
                .ticketNo("ticketNo-001")
                .price(10000L)
                .statusCode(StatusCode.DELIVERED.name())
                .build();

        //then
        mockMvc.perform(post(API_TICKET_URL + savedFestivalDetail.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketCreateDto))
                .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("data.ticketNo").value("ticketNo-001"))
                .andExpect(jsonPath("data.price").value(10000L));
//                .andDo(document("{method-name}",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        requestFields(
//                                fieldWithPath("ticketNo").type(JsonFieldType.STRING).description("티켓번호"),
//                                fieldWithPath("statusCode").type(JsonFieldType.STRING).description("주문상태코드"),
//                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("티켓가격")
//                        ),
//                        responseFields(beneathPath("data").withSubsectionId("data"),
//                                fieldWithPath("ticketId").type(JsonFieldType.NUMBER).description("티켓 ID"),
//                                fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("오더 ID"),
//                                fieldWithPath("ticketNo").type(JsonFieldType.STRING).description("티켓번호"),
//                                fieldWithPath("festivalDetailId").type(JsonFieldType.NUMBER).description("축제 디테일 ID"),
//                                fieldWithPath("statusCode").type(JsonFieldType.STRING).description("주문상태코드"),
//                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("티켓가격"),
//                                fieldWithPath("createDate").type(JsonFieldType.NUMBER).description("생성일자"),
//                                fieldWithPath("createId").type(JsonFieldType.NUMBER).description("생성인"),
//                                fieldWithPath("modifyDate").type(JsonFieldType.NUMBER).description("수정일자"),
//                                fieldWithPath("modifyId").type(JsonFieldType.NUMBER).description("수정인")
//                        )
//                ));
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @DisplayName("티켓 상태업데이트 테스트")
    public void ticketUpdateTest() throws Exception{
        //given
        TicketUpdateDto ticketUpdateDto = TicketUpdateDto.builder()
                .statusCode(StatusCode.SHIPPING.name())
                .build();

        //then
        mockMvc.perform(put(API_TICKET_URL + savedTicket.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketUpdateDto))
                .with(csrf()))
                .andExpect(jsonPath("data.statusCode").value("SHIPPING"))
                .andDo(print());
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @DisplayName("티켓 삭제 테스트")
    public void ticketDeleteTest() throws Exception{
        mockMvc.perform(delete(API_TICKET_URL + savedTicket.getId())
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(value = "mock-manager", roles = "MANAGER")
    @DisplayName("티켓 조회 테스트")
    public void ticketDetailTest() throws Exception{
        mockMvc.perform(get(API_TICKET_URL + savedTicket.getId())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.price").value(10000))
                .andExpect(jsonPath("data.ticketNo").value("ticketNo-001"))
                .andExpect(jsonPath("data.statusCode").value("DELIVERED"))
                .andDo(print());
    }

}