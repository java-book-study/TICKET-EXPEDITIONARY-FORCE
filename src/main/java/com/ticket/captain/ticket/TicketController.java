package com.ticket.captain.ticket;

import com.ticket.captain.response.ApiResponseDto;
import com.ticket.captain.ticket.dto.TicketCreateDto;
import com.ticket.captain.ticket.dto.TicketDto;
import com.ticket.captain.ticket.dto.TicketUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/ticket")
    public ApiResponseDto<TicketDto> add(@RequestBody TicketCreateDto create) {
        return ApiResponseDto.createOK(ticketService.add(create.toDto()));
    }

    @PutMapping("/ticket/{id}")
    public ApiResponseDto<TicketDto> update(Long id, @Valid TicketUpdateDto update) {
        return ApiResponseDto.createOK(ticketService.update(id, update.toDto()));
    }
}
