package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter @Setter
@ToString
public class FestivalResponseDto {

    private Long id;

    private String title;

    private String Thumbnail;

    private String content;

    private LocalDateTime salesStartDate;

    private LocalDateTime salesEndDate;

    private Long createId;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private Long modifyId;

    public FestivalResponseDto(Festival source) {
        copyProperties(source, this);
    }


}
