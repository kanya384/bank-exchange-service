package ru.bank.processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bank.processing.dto.AccountResponseDTO;
import ru.bank.processing.dto.NewAccountDTO;
import ru.bank.processing.dto.PutAccountMoneyDTO;
import ru.bank.processing.entity.Account;
import ru.bank.processing.mapper.AccountMapper;
import ru.bank.processing.repository.AccountRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountResponseDTO createNewAccount(NewAccountDTO dto) {
        Account account = accountMapper.map(dto);
        account = repository.save(account);
        return accountMapper.map(account);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public AccountResponseDTO addMoneyToAccount(Long accountId, PutAccountMoneyDTO data) {
        Account account = repository.findById(accountId)
                .orElseThrow(()-> new NoSuchElementException("not found account with id = " + accountId));

        account.setBalance(account.getBalance() + data.getMoney());

        return accountMapper.map(account);
    }

    public Account getAccountById(Long id) {
        return repository.findById(id).orElseThrow(()-> new NoSuchElementException("not found account with id " +id));
    }

    public AccountResponseDTO readAccountById(Long id) {
        return repository.findById(id)
                .map(accountMapper::map)
                .orElseThrow(()-> new NoSuchElementException("not found account with id " +id));
    }

    public List<AccountResponseDTO> readAccountsOfUser(Long id) {
        return repository.findByUserId(id)
                .stream()
                .map(accountMapper::map)
                .toList();
    }
}
