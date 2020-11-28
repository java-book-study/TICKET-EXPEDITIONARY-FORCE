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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderQueryRepository orderQueryRepository;

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

    public List<OrderDto> findByAccount(Pageable pageable, Long accountId) {

        return orderQueryRepository.findByAccountId(pageable, accountId).stream()
                .map(OrderDto::of)
                .collect(Collectors.toList());
    }

    public List<OrderDto> findByAccountWithDate(Pageable pageable, Long accountId, LocalDateTime startDate, LocalDateTime endDate) {

        return orderQueryRepository.findByAccountWithDate(pageable, accountId, startDate, endDate).stream()
                .map(OrderDto::of)
                .collect(Collectors.toList());
    }
}
