package com.ticket.captain.account.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountUpdateDto {

        @Length(min=2, max=20)
        private String name;
        @Email
        private String email;

}
