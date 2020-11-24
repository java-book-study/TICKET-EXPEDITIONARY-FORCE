package com.ticket.captain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class OrderServiceTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Order create 확인")
    public void orderServiceTest() throws Exception{
        //given

        //when

        //then

    }
}