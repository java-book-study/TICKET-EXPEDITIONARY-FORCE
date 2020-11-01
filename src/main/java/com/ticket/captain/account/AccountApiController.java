package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 은성님 controller 코드
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/account/**")
public class AccountApiController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @GetMapping("")
    public ApiResponseDto<?> accountList(@PageableDefault Pageable pageable){


        Page<AccountDto.Response> result = accountService.findAccountList(pageable);

        return ApiResponseDto.createOK(result);
    }

    @GetMapping("{id}")
    public ApiResponseDto<?> accountDetail(@PathVariable Long id){


        AccountDto.Response result = accountService.findAccountDetail(id);

        return ApiResponseDto.createOK(result);
    }

    @PostMapping("{id}")
    public ApiResponseDto<?> accountUpdate(@PathVariable Long id, @RequestBody @Valid AccountDto.Update updateRequestDto){


        accountService.accountUpdate(id, updateRequestDto);

        return ApiResponseDto.DEFAULT_OK;
    }

    //해당 appoint로 한 이유는 이 api는 ADMIN만 접근할 수 있게끔 할 것이다.
    //SecurityConfig 내에서 /api/account/ 는 permitAll이기 때문에(일시적임, 마무리 때에 수정해야 할 사항)
    // /api/appoint/ 는 ADMIN 접근으로 설정해둠
    @PutMapping("/api/appoint/{id}")
    public ApiResponseDto<Account> managerAppoint(@PathVariable Long id){
        accountService.managerAppoint(id);
        //밑에 2줄은 테스트코드를 위한 코드
        Account findAccount = accountRepository.findById(id).orElseThrow(NullPointerException::new);
        return ApiResponseDto.createOK(findAccount);
    }
}
