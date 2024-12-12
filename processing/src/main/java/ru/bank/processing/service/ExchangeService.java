package ru.bank.processing.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bank.processing.dto.PutAccountMoneyDTO;
import ru.bank.processing.entity.Account;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final String CURRENCY_RUB = "RUB";

    private static final Logger log = LoggerFactory.getLogger(ExchangeService.class);

    private final AccountService accountService;
    private final CurrencyService currencyService;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Double exchangeCurrency(String uuid, Long fromAccount, Long toAccount, Double amount) {
        Account source = accountService.getAccountById(fromAccount);
        Account target = accountService.getAccountById(toAccount);

        log.info("Exchange operation {} from account {} to account {} started", uuid, fromAccount, toAccount);

        Double result;
        if (!CURRENCY_RUB.equals(source.getCurrencyCode()) && CURRENCY_RUB.equals(target.getCurrencyCode())) {
            Double rate = currencyService.loadCurrencyRate(source.getCurrencyCode());
            result = exchangeWithMultiply(uuid, source, target, rate, amount);
        } else if (CURRENCY_RUB.equals(source.getCurrencyCode()) && !CURRENCY_RUB.equals(target.getCurrencyCode())) {
            Double rate = currencyService.loadCurrencyRate(target.getCurrencyCode());
            Double multiplier = 1 / rate;
            result = exchangeWithMultiply(uuid, source, target, multiplier, amount);
        } else if (!CURRENCY_RUB.equals(source.getCurrencyCode()) && !CURRENCY_RUB.equals(target.getCurrencyCode())) {
            Double rateFrom = currencyService.loadCurrencyRate(source.getCurrencyCode());
            Double rateTo = currencyService.loadCurrencyRate(target.getCurrencyCode());
            result = exchangeThroughRub(uuid, source, target, rateFrom, rateTo, amount);
        } else if (source.getCurrencyCode().equals(target.getCurrencyCode())) {
            result = simpleExchange(uuid, source, target, amount);
        } else {
            throw new IllegalStateException("Unknown behaviour");
        }

        log.info("Exchange operation {} from account {} to account {} completed", uuid, fromAccount, toAccount);

        return result;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private Double simpleExchange(String uuid, Account source, Account target, Double amount) {
        accountService.addMoneyToAccount(source.getId(), PutAccountMoneyDTO.builder()
                        .money(-amount)
                        .uid(uuid)
                .build());

        accountService.addMoneyToAccount(target.getId(), PutAccountMoneyDTO.builder()
                .money(amount)
                .uid(uuid)
                .build());

        return amount;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private Double exchangeThroughRub(String uuid, Account source, Account target,
                                     Double rateFrom, Double rateTo, Double amount) {
        accountService.addMoneyToAccount(source.getId(), PutAccountMoneyDTO.builder()
                .money(-amount)
                .uid(uuid)
                .build());

        Double rub = amount * rateFrom;
        Double result = rub / rateTo;
        accountService.addMoneyToAccount(target.getId(), PutAccountMoneyDTO.builder()
                .money(result)
                .uid(uuid)
                .build());

        return result;
    }

    @Transactional
    private Double exchangeWithMultiply(String uuid, Account source, Account target, Double rate, Double amount) {
        accountService.addMoneyToAccount(source.getId(), PutAccountMoneyDTO.builder()
                .money(-amount)
                .uid(uuid)
                .build());
        Double result = amount * rate;
        accountService.addMoneyToAccount(target.getId(), PutAccountMoneyDTO.builder()
                .money(result)
                .uid(uuid)
                .build());
        return  result;
    }
}
