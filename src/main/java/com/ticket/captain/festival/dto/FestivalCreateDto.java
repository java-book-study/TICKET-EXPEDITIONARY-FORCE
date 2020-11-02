package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class FestivalCreateDto {

    @NotBlank
    @Length(min = 2, max = 20)
    private String name;

    private String Thumbnail;

    @NotBlank
    @Length(min = 2, max = 1000)
    private String content;

    @NotBlank
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
