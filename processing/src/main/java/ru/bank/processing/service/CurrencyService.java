package ru.bank.processing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final RestTemplate restClient;

    @Value("${service.currency.url}")
    private String currencyUrl;

    public Double loadCurrencyRate(String code) {
        return restClient.getForObject(currencyUrl + "/currency/rate/{code}", Double.class, code);
    }
}
