package com.ticket.captain.order;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountRepository;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festival.Festival;
import com.ticket.captain.festival.FestivalRepository;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.festivalDetail.FestivalDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final FestivalRepository festivalRepository;
    private final FestivalDetailRepository festivalDetailRepository;
    private final AccountRepository accountRepository;

    public String createOrder(Long festival_sq, String userEmail) {
        FestivalDetail curFestivalDetail = festivalDetailRepository.findById(festival_sq).orElseThrow(NotFoundException::new);
        Long festivalId = curFestivalDetail.getFestival().getId();
        Account curAccount = accountRepository.findByEmail(userEmail);
        StatusCode statusCode = StatusCode.PURCHASE;
        String orderNo = LocalDateTime.now()+"0001";
        Order createdOrder = Order.builder()
                .orderNo(orderNo)
                .festivalId(festivalId)
                .festivalDetail(curFestivalDetail)
                .account(curAccount)
                .statusCode(statusCode)
                .build();
        return orderRepository.save(createdOrder).getOrderNo();
    }
}
