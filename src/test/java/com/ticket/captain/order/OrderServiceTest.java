package com.ticket.captain.order;

import com.ticket.captain.account.Account;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.festivalDetail.FestivalDetailRepository;
import com.ticket.captain.order.dto.OrderDto;
import com.ticket.captain.ticket.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class OrderServiceTest {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private FestivalDetailRepository festivalDetailRepository;
    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("Order 생성 확인")
    public void orderServiceTest() throws Exception{
        //given
        FestivalDetail festivalDetail = new
        FestivalDetail festivalDetailId = festivalDetailRepository.save(festivalDetail);
        Account account = Account.builder()
                .email("test@gmail.com")
                .build();

        //when
        orderService.createOrder(festivalDetailId.getId(), "test@gmail.com");

        //then

    }
}