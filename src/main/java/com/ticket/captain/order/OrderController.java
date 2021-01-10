package com.ticket.captain.order;

import com.ticket.captain.account.Account;
import com.ticket.captain.order.dto.OrderDto;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{festivalDetailId}")
    public ApiResponseDto<OrderDto> createOrder(@PathVariable Long festivalDetailId){
        return ApiResponseDto.createOK(orderService.createOrder(festivalDetailId));
    }

    @GetMapping("/buyer")
    public ResponseEntity<?> myOrder(@AuthenticationPrincipal User user, Pageable pageable,
                                     @RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate) {
//        Long accountId = account.getId();
        Long accountId = 1L;

        List<OrderDto> buyerOrders =
                startDate != null && endDate != null
                        ? orderService.findByAccountWithDate(pageable, accountId, startDate, endDate)
                        : orderService.findByAccount(pageable, accountId);

        return ResponseEntity.ok(buyerOrders);
    }
}
