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

    public OrderDto createOrder(Long festivalDetailId, String userEmail) {

        FestivalDetail curFestivalDetail =
                festivalDetailRepository.findById(festivalDetailId).orElseThrow(NotFoundException::new);

        Festival findFestival = curFestivalDetail.getFestival();
        Account curAccount = accountRepository.findByEmail(userEmail);
        String statusCode = StatusCode.PURCHASE.name();
        String orderNo = UUID.randomUUID().toString();

        //Order 생성
        Order createdOrder = Order.createOrder(orderNo, festivalDetailId, curFestivalDetail, curAccount, statusCode);

        //Ticket 생성
//        Ticket createdTicket = Ticket.createTicket(orderNo, statusCode, curFestivalDetail.getPrice());

        //Order -> Ticket 매핑
//        createdOrder.addTicket(createdTicket);

        Order savedOrder = orderRepository.save(createdOrder);
        return OrderDto.of(savedOrder);
    }
}
