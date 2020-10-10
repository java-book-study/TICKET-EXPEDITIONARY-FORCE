package com.ticket.captain.ticket;

import com.ticket.captain.ApiDocumentationTest;
import com.ticket.captain.ticket.dto.TicketCreateDto;
import com.ticket.captain.ticket.dto.TicketDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TicketCreateDocumentationTests extends ApiDocumentationTest {
    @Test
    public void add() throws Exception {

        given(ticketService.add(any(TicketCreateDto.class)))
                .willReturn(TicketDto.builder()
                        .id(1L)
                        .title("Ticket1")
                        .amount(10)
                        .build());

        Request request = new Request();
        request.title = "Ticket1";
        request.amount = "10";

        ResultActions result = this.mockMvc.perform(
                post("/ticket")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
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
        String title;
        String amount;
    }
}
