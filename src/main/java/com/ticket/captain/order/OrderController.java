package com.ticket.captain.order;

import com.ticket.captain.order.dto.OrderDto;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/createOrder/{festival_sq}")
    public ApiResponseDto<OrderDto> createOrder(@PathVariable Long festivalDetailId, @AuthenticationPrincipal User user){
        return ApiResponseDto.createOK(orderService.createOrder(festivalDetailId, user.getUsername()));
    }
}
