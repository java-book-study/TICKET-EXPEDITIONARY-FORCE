package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.account.dto.AccountPutDto;
import com.ticket.captain.account.dto.AccountRoleDto;
import com.ticket.captain.account.dto.AccountUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 은성님 controller 코드
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/api/account" , produces = MediaTypes.HAL_JSON_VALUE)
public class AccountApiController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @GetMapping("")
    public ResponseEntity<?> accountList(@PageableDefault Pageable pageable,
                                         PagedResourcesAssembler<AccountDto> assembler){

        Page<AccountDto> result = accountService.findAccountList(pageable);
        PagedModel<EntityModel<AccountDto>> accountDtoModel = assembler.toModel(result, AccountResource::of);
        accountDtoModel.add(Link.of("/docs/index.html#list-accounts").withRel("profile"));
        return ResponseEntity.ok(accountDtoModel);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> accountDetail(@PathVariable Long id){
        AccountDto result = accountService.findAccountDetail(id);
        EntityModel<AccountDto> accountDtoModel = AccountResource.of(result);
        accountDtoModel.add(Link.of("/docs/index.html#get-account").withRel("profile"));
        return ResponseEntity.ok(accountDtoModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> accountUpdate(@PathVariable Long id, @RequestBody @Valid AccountUpdateDto updateRequestDto){

        AccountPutDto accountPutDto = accountService.accountUpdate(id, updateRequestDto);
        EntityModel<AccountPutDto> accountPutDtoModel = AccountResource.of(accountPutDto);
        accountPutDtoModel.add(Link.of("/docs/index.html#update-account").withRel("profile"));
        return ResponseEntity.ok(accountPutDtoModel);
    }

    @PutMapping("appoint/{id}")
    public ResponseEntity<?> roleAppoint(@PathVariable Long id, @RequestParam Role role){
        AccountRoleDto accountRoleDto = accountService.roleAppoint(id, role);
        EntityModel<AccountRoleDto> accountRoleDtoModel = AccountResource.of(accountRoleDto);
        accountRoleDtoModel.add(Link.of("/docs/index.html#grant-account").withRel("profile"));
        return ResponseEntity.ok(accountRoleDtoModel);
    }
}
