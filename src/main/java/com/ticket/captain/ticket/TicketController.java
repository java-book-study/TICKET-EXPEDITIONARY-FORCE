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
    public ApiResponseDto<List<TicketDto>> ticketList() {
        return ApiResponseDto.createOK(ticketService.findAll());
    }

    @GetMapping("/{ticketId}")
    public ApiResponseDto<TicketDto> ticketDetail(@PathVariable("ticketId") Long ticketId) {
        return ApiResponseDto.createOK(ticketService.findById(ticketId));
    }

    @PostMapping("/{festivalDetailId}")
    public ApiResponseDto<TicketDto> ticketAdd(@PathVariable("festivalDetailId") Long festivalDetailId,
                                               @RequestBody TicketCreateDto ticketCreateDto) {
        return ApiResponseDto.createOK(ticketService.add(festivalDetailId, ticketCreateDto.toDto()));
    }

    @PutMapping("/{ticketId}")
    public ApiResponseDto<TicketDto> ticketUpdate(@PathVariable("ticketId") Long ticketId,
                                                  @Valid @RequestBody TicketUpdateDto ticketUpdateDto) {
        return ApiResponseDto.createOK(ticketService.update(ticketId, ticketUpdateDto.toDto()));
    }

    @DeleteMapping("/{ticketId}")
    public ApiResponseDto<String> delete(@PathVariable("ticketId") Long ticketId) {
        ticketService.delete(ticketId);

        return ApiResponseDto.DEFAULT_OK;
    }
}
