package com.ticket.captain.order;

import com.ticket.captain.order.dto.OrderDto;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class OrderResource extends EntityModel<OrderDto> {

    public static EntityModel<OrderDto> of(OrderDto orderDto){
        return EntityModel.of(orderDto).add(linkTo(OrderController.class).slash(orderDto.getOrderNo()).withSelfRel());
    }
}
