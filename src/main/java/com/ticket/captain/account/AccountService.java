package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountResponseDto;
import com.ticket.captain.account.dto.AccountUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    public Page<AccountResponseDto> findAccountList(Pageable pageable) {

        Page<AccountResponseDto> result = accountRepository.findAll(pageable)
                .map(AccountResponseDto::new);

        return result;
    }

    public AccountResponseDto findAccountDetail(Long id){

        AccountResponseDto result = accountRepository.findById(id)
                .map(AccountResponseDto::new)
                .orElseThrow(NullPointerException::new);

        return result;
    }

    @Transactional
    public void accountUpdate(Long id, AccountUpdateRequestDto requestDto) {

        Account account = accountRepository.findById(id)
                .orElseThrow(NullPointerException::new);
        account.update(requestDto);
        accountRepository.save(account);
    }
}
