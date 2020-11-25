package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.account.dto.AccountPutDto;
import com.ticket.captain.account.dto.AccountRoleDto;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class AccountResource extends EntityModel<AccountDto> {

    public static EntityModel<AccountDto> of(AccountDto accountDto){
        return EntityModel.of(accountDto).add(linkTo(AccountApiController.class).slash(accountDto.getId()).withSelfRel());
    }
    public static EntityModel<AccountPutDto> of(AccountPutDto accountPutDto){
        return EntityModel.of(accountPutDto).add(linkTo(AccountApiController.class).slash(accountPutDto.getId()).withSelfRel());
    }
    public static EntityModel<AccountRoleDto> of(AccountRoleDto accountRoleDto){
        return EntityModel.of(accountRoleDto).add(linkTo(AccountApiController.class).slash(accountRoleDto.getId()).withSelfRel());
    }
}
