package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class FestivalCreateDto {

    @NotBlank
    @Length(min = 2, max = 20)
    private String title;

    private String Thumbnail;

    @NotBlank
    @Length(min = 2, max = 1000)
    private String content;

    private LocalDateTime salesStartDate;

    private LocalDateTime salesEndDate;

    private Long createId;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private Long modifyId;

    public Festival newFestival() {
        return new Festival(title, content, Thumbnail, salesStartDate, salesEndDate, createId, modifyDate, modifyId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("Thumbnail", Thumbnail)
                .append("content", content)
                .append("salesStartDate", salesStartDate)
                .append("salesEndDate", salesEndDate)
                .append("createId", createId)
                .append("createDate", createDate)
                .append("modifyDate", modifyDate)
                .append("modifyId", modifyId)
                .toString();
    }
}
