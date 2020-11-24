package com.ticket.captain.order;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountRepository;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.festivalDetail.FestivalDetailRepository;
import com.ticket.captain.order.dto.OrderDto;
import com.ticket.captain.ticket.Ticket;
import com.ticket.captain.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final FestivalDetailRepository festivalDetailRepository;
    private final AccountRepository accountRepository;
    private final TicketRepository ticketRepository;

    public OrderDto createOrder(Long festival_sq, String userEmail) {
        FestivalDetail curFestivalDetail = festivalDetailRepository.findById(festival_sq).orElseThrow(NotFoundException::new);
        Festival findFestival = curFestivalDetail.getFestival();
        Account curAccount = accountRepository.findByEmail(userEmail);
        String statusCode = StatusCode.PURCHASE.name();
        String orderNo = UUID.randomUUID().toString();

        Order createdOrder = Order.builder()
                .orderNo(orderNo)
                .festivalId(findFestival.getId())
                .festivalDetail(curFestivalDetail)
                .account(curAccount)
                .statusCode(statusCode)
                .build();

        Ticket createdTicket = Ticket.builder()
                .ticketNo(orderNo)
                .price(curFestivalDetail.getPrice())
                .statusCode(statusCode)
                .build();

        createdOrder.addTicket(createdTicket);
        ticketRepository.save(createdTicket);
        Order savedOrder = orderRepository.save(createdOrder);
        return OrderDto.of(savedOrder);
    }
}
