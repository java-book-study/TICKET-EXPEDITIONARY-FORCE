package com.ticket.captain.ticket;

import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.festivalDetail.FestivalDetail;
import com.ticket.captain.festivalDetail.FestivalDetailRepository;
import com.ticket.captain.order.Order;
import com.ticket.captain.order.OrderRepository;
import com.ticket.captain.order.dto.OrderDto;
import com.ticket.captain.ticket.dto.TicketCreateDto;
import com.ticket.captain.ticket.dto.TicketDto;
import com.ticket.captain.ticket.dto.TicketUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FestivalDetailRepository festivalDetailRepository;
    private final OrderRepository orderRepository;

    public List<TicketDto> findAll() {
        return ticketRepository.findAll().stream()
                .map(TicketDto::of)
                .collect(Collectors.toList());
    }

    public TicketDto findById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .map(TicketDto::of)
                .orElseThrow(NotFoundException::new);
    }

    public TicketDto add(Long festivalDetailId, Long orderId, TicketCreateDto ticketCreateDto) {
        FestivalDetail findFestivalDetail = festivalDetailRepository.findById(festivalDetailId)
                .orElseThrow(NotFoundException::new);
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);

        Ticket target = ticketCreateDto.toEntity();
        target.festivalDetailSetting(findFestivalDetail);
        target.orderSetting(findOrder);
        Ticket createdTicket = ticketRepository.save(target);

        return TicketDto.of(createdTicket);
    }

    public void delete(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(NotFoundException::new);

        ticketRepository.delete(ticket);
    }

    public TicketDto update(Long ticketId, TicketUpdateDto ticketUpdateDto) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(NotFoundException::new);

        ticketUpdateDto.apply(ticket);

        return TicketDto.of(ticket);
    }
}
