package com.ticket.captain.order;

import com.ticket.captain.order.dto.OrderDto;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{orderNo}")
    public ResponseEntity<?> findByOrderNo(@PathVariable String orderNo) {
        EntityModel<OrderDto> order = OrderResource.of(orderService.findByOrderNo(orderNo));
        order.add(Link.of("/docs/index.html#get-order").withRel("document"));

        return ResponseEntity.ok(order);
    }

    @GetMapping("/buyer")
    public ResponseEntity<?> myOrder(@AuthenticationPrincipal User user,
                                     Pageable pageable,
                                     PagedResourcesAssembler<OrderDto> assembler,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate) {
        String accountEmail = user.getUsername();

        Page<OrderDto> orders =
                startDate != null && endDate != null
                        ? orderService.findByAccountWithDate(pageable, accountEmail, startDate, endDate)
                        : orderService.findByAccount(pageable, accountEmail);

        PagedModel<EntityModel<OrderDto>> pagedOrders = assembler.toModel(orders, OrderResource::of);
        pagedOrders.add(Link.of("/docs/index.html#buyer-orders").withRel("document"));

        return ResponseEntity.ok(pagedOrders);
    }

    @PostMapping("/{festivalDetailId}")
    public ApiResponseDto<OrderDto> createOrder(
            @AuthenticationPrincipal User user,
            @PathVariable Long festivalDetailId) {
        String accountEmail = user.getUsername();

        return ApiResponseDto.createOK(orderService.createOrder(accountEmail, festivalDetailId));
    }
}
