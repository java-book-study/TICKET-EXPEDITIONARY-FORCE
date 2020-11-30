package com.ticket.captain.festival.dto;

import com.ticket.captain.festival.Festival;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


/**
 * 생성할 때 사용하는 Dto 이다.
 * 입력되는 값만 필드로 갖고 있기 때문에 Long id 등의 필드는 존재하지 않는다.
 */
@Getter
@NoArgsConstructor
public class FestivalCreateDto {

    private String title;

    private String thumbnail;

    private String content;

    private LocalDateTime salesStartDate;

    private LocalDateTime salesEndDate;

    private String festivalCategory;

    public Festival toEntity() {

        return Festival.builder()
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .salesStartDate(salesStartDate)
                .salesEndDate(salesEndDate)
                .festivalCategory(festivalCategory)
                .build();
    }
}
