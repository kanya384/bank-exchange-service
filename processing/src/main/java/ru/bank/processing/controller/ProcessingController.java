package ru.bank.processing.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bank.processing.dto.AccountResponseDTO;
import ru.bank.processing.dto.ExchangeMoneyDTO;
import ru.bank.processing.dto.NewAccountDTO;
import ru.bank.processing.dto.PutAccountMoneyDTO;
import ru.bank.processing.service.AccountService;
import ru.bank.processing.service.ExchangeService;

import java.util.List;

@RestController
@RequestMapping("processing")
@RequiredArgsConstructor
public class ProcessingController {
    private final AccountService accountService;

    private final ExchangeService exchangeService;

    @GetMapping("/account/{id}")
    public AccountResponseDTO readAccount(@PathVariable Long id) {
        return accountService.readAccountById(id);
    }

    @GetMapping("/user/{id}")
    public List<AccountResponseDTO> readAccountOfUser(@PathVariable("id") Long userId) {
        return accountService.readAccountsOfUser(userId);
    }

    @PostMapping("/account")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO createAccount(@RequestBody NewAccountDTO data) {
        return accountService.createNewAccount(data);
    }

    @PutMapping("/account/{id}")
    public AccountResponseDTO putMoneyToAccount(@PathVariable("id") Long accountId, @Valid @RequestBody PutAccountMoneyDTO data) {
        return accountService.addMoneyToAccount(accountId, data);
    }

    @PutMapping("/exchange/{uid}")
    public Double exchange(@PathVariable("uid") String uid, @Valid @RequestBody ExchangeMoneyDTO data) {
        return exchangeService.exchangeCurrency(uid, data.getFrom(), data.getTo(), data.getAmount());
    }
}
