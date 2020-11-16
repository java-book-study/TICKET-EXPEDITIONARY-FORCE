package com.ticket.captain.salesType.dto;

import com.ticket.captain.salesType.SalesType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SalesTypeDto {

    private Long id;

    private String name;

    private LocalDateTime createDate;

    private Long createId;

    private LocalDateTime modifyDate;

    private Long modifyId;

    @Builder
    private SalesTypeDto(Long id, String name, LocalDateTime createDate, Long createId, LocalDateTime modifyDate, Long modifyId) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createId = createId;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public static SalesTypeDto of(SalesType salesType) {

        return SalesTypeDto.builder()
                .id(salesType.getId())
                .name(salesType.getName())
                .createId(salesType.getCreateId())
                .createDate(salesType.getCreateDate())
                .modifyId(salesType.getModifyId())
                .modifyDate(salesType.getModifyDate())
                .build();
    }
}
