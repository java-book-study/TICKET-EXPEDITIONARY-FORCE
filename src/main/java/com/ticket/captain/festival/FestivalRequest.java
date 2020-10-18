package com.ticket.captain.festival;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class FestivalRequest {

    private String name;

    private String Thumbnail;

    private String content;

    private int winners;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Festival newFestival() {
        return new Festival(name, content, winners, Thumbnail, startDate, endDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("Thumbnail", Thumbnail)
                .append("content", content)
                .append("winners", winners)
                .append("startDate", startDate)
                .append("endDate", endDate)
                .toString();
    }
}
