package com.ticket.captain.ticket;

import com.ticket.captain.response.ApiResponseDto;
import com.ticket.captain.ticket.dto.TicketCreateDto;
import com.ticket.captain.ticket.dto.TicketDto;
import com.ticket.captain.ticket.dto.TicketUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping({"", "/"})
    public ApiResponseDto<List<TicketDto>> ticketList(Long ticketId) {
        return ApiResponseDto.createOK(ticketService.findAll());
    }

    @GetMapping("{ticketId}")
    public ApiResponseDto<TicketDto> ticketDetail(Long ticketId) {
        return ApiResponseDto.createOK(ticketService.findById(ticketId));
    }

    @PostMapping({"", "/"})
    public ApiResponseDto<TicketDto> ticketAdd(@RequestBody TicketCreateDto create) {
        return ApiResponseDto.createOK(ticketService.add(create.toDto()));
    }

    @PutMapping("{ticketId}")
    public ApiResponseDto<TicketDto> ticketUpdate(Long ticketId, @Valid TicketUpdateDto update) {
        return ApiResponseDto.createOK(ticketService.update(ticketId, update.toDto()));
    }
}
