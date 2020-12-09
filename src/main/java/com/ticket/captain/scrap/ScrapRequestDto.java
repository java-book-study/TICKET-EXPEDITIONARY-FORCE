package com.ticket.captain.scrap;

import com.ticket.captain.account.Account;
import com.ticket.captain.festivalDetail.FestivalDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScrapRequestDto {

    private Long accountId;

    private Long festivalDetailId;

    public static Scrap toEntity(Account account, FestivalDetail festivalDetail){

        return Scrap.builder()
                .account(account)
                .festivalDetail(festivalDetail)
                .build();
    }
}
