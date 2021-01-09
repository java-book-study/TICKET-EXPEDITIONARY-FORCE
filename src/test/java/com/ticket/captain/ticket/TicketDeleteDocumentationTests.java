//package com.ticket.captain.ticket;
//
//import com.ticket.captain.ApiDocumentationTest;
//import org.junit.Test;
//import org.junit.jupiter.api.DisplayName;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentRequest;
//import static com.ticket.captain.document.utils.ApiDocumentUtils.getDocumentResponse;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class TicketDeleteDocumentationTests extends ApiDocumentationTest {
//
//    @Test
//    @WithMockUser(value = "mock-user", roles = "USER")
//    @DisplayName("Ticket Delete 테스트")
//    public void ticket_delete_by_id() throws Exception {
//
//        doNothing()
//                .when(ticketService)
//                .delete(1L);
//
//        ResultActions result = this.mockMvc.perform(
//                RestDocumentationRequestBuilders.delete("/api/ticket/{ticketId}", 1L)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .with(csrf())
//        );
//
//        result.andExpect(status().isOk())
//                .andDo(document("{method-name}",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        pathParameters(
//                                parameterWithName("ticketId").description("티켓ID")
//                        )
//                ));
//    }
//}
