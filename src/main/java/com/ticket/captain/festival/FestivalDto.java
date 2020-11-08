package com.ticket.captain.festival;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter @Setter
public class FestivalDto {

    private Long id;

    private String name;

    private String Thumbnail;

    private String content;

    private int winners;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime createDate;

    public FestivalDto(Festival source) {
        copyProperties(source, this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("Thumbnail", Thumbnail)
                .append("content", content)
                .append("winners", winners)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .append("createDate", createDate)
                .toString();
    }
}
