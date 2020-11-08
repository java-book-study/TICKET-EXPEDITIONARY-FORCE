package com.ticket.captain.ticket;

import com.ticket.captain.ApiDocumentationTest;
import com.ticket.captain.ticket.dto.TicketDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TicketListDocumentationTests extends ApiDocumentationTest {

    @Test
    @WithMockUser(value = "mock-user", roles = "USER")
    @DisplayName("Ticket findById 테스트")
    public void ticket_find() throws Exception {

        given(ticketService.findById(1L))
                .willReturn(TicketDto.builder()
                        .ticketId(1L)
                        .ticketNo("IU00000001")
                        .orderNo("20110100000000001")
                        .festivalId(1L)
                        .festivalSq(1L)
                        .statusCode(2L)
                        .price(110000L)
                        .build());

        ResultActions result = this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/ticket/{ticketId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("{method-name}",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("ticketId").description("티켓 ID")
                        ),
                        responseFields(beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("ticketId").type(JsonFieldType.NUMBER).description("티켓 ID"),
                                fieldWithPath("ticketNo").type(JsonFieldType.STRING).description("티켓번호"),
                                fieldWithPath("orderNo").type(JsonFieldType.STRING).description("주문번호"),
                                fieldWithPath("festivalId").type(JsonFieldType.NUMBER).description("축제 ID"),
                                fieldWithPath("festivalSq").type(JsonFieldType.NUMBER).description("축제 순번"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("주문상태코드"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("티켓가격")
                        )
                ));
    }

    @Test
    @WithMockUser(value = "mock-user", roles = "USER")
    @DisplayName("Ticket findByAll 테스트")
    public void ticket_find_all() throws Exception {

        List<TicketDto> responseList = Arrays.asList(
                TicketDto.builder()
                        .ticketId(1L)
                        .ticketNo("IU00000001")
                        .orderNo("20110100000000001")
                        .festivalId(1L)
                        .festivalSq(1L)
                        .statusCode(2L)
                        .price(110000L)
                        .build()
        );

        given(ticketService.findAll())
                .willReturn(responseList);

        ResultActions result = this.mockMvc.perform(
                get("/api/ticket")
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(document("{method-name}",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(beneathPath("data[]").withSubsectionId("data"),
                                fieldWithPath("ticketId").type(JsonFieldType.NUMBER).description("티켓 ID"),
                                fieldWithPath("ticketNo").type(JsonFieldType.STRING).description("티켓번호"),
                                fieldWithPath("orderNo").type(JsonFieldType.STRING).description("주문번호"),
                                fieldWithPath("festivalId").type(JsonFieldType.NUMBER).description("축제 ID"),
                                fieldWithPath("festivalSq").type(JsonFieldType.NUMBER).description("축제 순번"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("주문상태코드"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("티켓가격")
                        )
                ));

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        String ticketId;
    }
}
