package com.ticket.captain.account.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountErrorDto {

    private String message;

    public static AccountErrorDto of(RuntimeException e){
        return AccountErrorDto.builder()
                .message(e.getMessage())
                .build();
    }

}
