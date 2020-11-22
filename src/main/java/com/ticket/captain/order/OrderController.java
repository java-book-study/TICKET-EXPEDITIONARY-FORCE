package com.ticket.captain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/createOrder/{festival_sq}")
    public String createOrder(@PathVariable Long festival_sq, HttpServletRequest request){
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");
        return orderService.createOrder(festival_sq, userEmail);
    }
}
