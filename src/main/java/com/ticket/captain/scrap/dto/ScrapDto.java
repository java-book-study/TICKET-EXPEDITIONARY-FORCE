package com.ticket.captain.scrap.dto;

import com.ticket.captain.scrap.Scrap;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ScrapDto {

    private Long scrapId;

    public static ScrapDto of(Scrap scrap) {

        return ScrapDto.builder()
                    .scrapId(scrap.getId())
                    .build();
    }
}
