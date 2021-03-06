package com.ticket.captain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.festival.FestivalService;
import com.ticket.captain.festivalDetail.FestivalDetailService;
import com.ticket.captain.order.OrderControllerTest;
import com.ticket.captain.order.OrderService;
import com.ticket.captain.ticket.TicketController;
import com.ticket.captain.ticket.TicketService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {
        TicketController.class,
        OrderControllerTest.class
})
@AutoConfigureRestDocs
public abstract class ApiDocumentationTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected FestivalService festivalService;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected FestivalDetailService festivalDetailService;

    @MockBean
    protected TicketService ticketService;
}
