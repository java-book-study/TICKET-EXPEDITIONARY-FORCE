package com.ticket.captain.salesType.dto;

import com.ticket.captain.salesType.SalesType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SalesTypeUpdateDto {

    private String name;

    private LocalDateTime modifyDate;

    private Long modifyId;

    @Builder
    private SalesTypeUpdateDto(String name, LocalDateTime modifyDate, Long modifyId) {
        this.name = name;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public SalesTypeUpdateDto toDto() {

        return SalesTypeUpdateDto.builder()
                .name(name)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .build();
    }

    public void apply(SalesType salesType) {
        salesType.update(name, modifyDate, modifyId);
    }
}
