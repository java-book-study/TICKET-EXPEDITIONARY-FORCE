package com.ticket.captain.order;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountRepository;
import com.ticket.captain.enumType.StatusCode;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.festivalDetail.FestivalDetailRepository;
import com.ticket.captain.order.dto.OrderCreateDto;
import com.ticket.captain.order.dto.OrderDto;
import com.ticket.captain.ticket.Ticket;
import com.ticket.captain.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderQueryRepository orderQueryRepository;

    private final OrderRepository orderRepository;
    private final FestivalDetailRepository festivalDetailRepository;
    private final AccountRepository accountRepository;
    private final TicketRepository ticketRepository;

    public OrderCreateDto createOrder(String accountEmail, Long festivalDetailId) {

        FestivalDetail curFestivalDetail =
                festivalDetailRepository.findById(festivalDetailId).orElseThrow(NotFoundException::new);

        Festival findFestival = curFestivalDetail.getFestival();
        Account account = accountRepository.findByEmail(accountEmail);
        String orderNo = UUID.randomUUID().toString();

        //Order 생성
        Order createdOrder = Order.createOrder(orderNo, findFestival, curFestivalDetail, account, StatusCode.PURCHASE);

        //Ticket 생성
        Ticket createdTicket = Ticket.createTicket(orderNo, StatusCode.PURCHASE.name(), curFestivalDetail.getPrice());

        //Order, Ticket 매핑
        createdTicket.orderSetting(createdOrder);
        createdTicket.festivalDetailSetting(curFestivalDetail);


        Order savedOrder = orderRepository.save(createdOrder);
        ticketRepository.save(createdTicket);

        return OrderCreateDto.of(savedOrder);
    }

    public OrderDto findByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo);
    }

    public Page<OrderDto> findByAccount(Pageable pageable, String accountEmail) {

        return orderQueryRepository.findByAccountId(pageable, accountEmail);
    }

    public Page<OrderDto> findByAccountWithDate(Pageable pageable, String accountEmail, LocalDateTime startDate, LocalDateTime endDate) {

        return orderQueryRepository.findByAccountWithDate(pageable, accountEmail, startDate, endDate);
    }
}
