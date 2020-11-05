package com.ticket.captain.ticket;

import com.ticket.captain.ApiDocumentationTest;
import com.ticket.captain.ticket.dto.TicketCreateDto;
import com.ticket.captain.ticket.dto.TicketDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TicketCreateDocumentationTests extends ApiDocumentationTest {
    @Test
    @DisplayName("Ticket Add 테스트")
    public void add() throws Exception {

        given(ticketService.add(any(TicketCreateDto.class)))
                .willReturn(TicketDto.builder()
                        .ticketId(2L)
                        .ticketNo("IU00000002")
                        .orderNo("20110100000000002")
                        .festivalId(1L)
                        .festivalSq(1L)
                        .statusCode(2L)
                        .price(new BigDecimal(110000))
                        .build());

        Request request = new Request();
        request.ticketNo = "IU00000002";
        request.orderNo = "20110100000000002";
        request.festivalId = "1";
        request.festivalSq = "1";
        request.statusCode = "2";
        request.price = "110000";

        ResultActions result = this.mockMvc.perform(
                post("/ticket")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        result.andExpect(status().isOk())
                .andDo(document("ticket/{method-name}",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("티켓명"),
                                fieldWithPath("amount").type(JsonFieldType.STRING).description("티켓개수").optional()
                        ),
                        responseFields(beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("티켓명"),
                                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("티켓개수")
                        )
                ));

    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        String ticketNo;
        String orderNo;
        String festivalId;
        String festivalSq;
        String statusCode;
        String price;
    }
}
