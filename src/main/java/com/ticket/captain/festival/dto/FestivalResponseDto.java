package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter @Setter
public class FestivalResponseDto {

    private Long id;

    private String name;

    private String Thumbnail;

    private String content;

    private int winners;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createDate;

    public FestivalResponseDto(Festival source) {
        copyProperties(source, this);
    }


}
