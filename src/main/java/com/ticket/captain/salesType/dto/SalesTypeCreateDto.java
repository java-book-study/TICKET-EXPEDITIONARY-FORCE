package com.ticket.captain.salesType.dto;

import com.ticket.captain.salesType.SalesType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SalesTypeCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    private LocalDateTime createDate;

    @NotBlank
    private Long createId;

    private LocalDateTime modifyDate;

    private Long modifyId;

    @Builder
    private SalesTypeCreateDto(String name, LocalDateTime createDate, Long createId, LocalDateTime modifyDate, Long modifyId) {
        this.name = name;
        this.createDate = createDate;
        this.createId = createId;
        this.modifyDate = modifyDate;
        this.modifyId = modifyId;
    }

    public SalesTypeCreateDto toDto() {

        return SalesTypeCreateDto.builder()
                .name(name)
                .createDate(createDate)
                .createId(createId)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .build();
    }

    public SalesType toEntity() {

        return SalesType.builder()
                .name(name)
                .createDate(createDate)
                .createId(createId)
                .modifyDate(modifyDate)
                .modifyId(modifyId)
                .build();
    }
}
