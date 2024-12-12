package com.laurkan.curency.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.laurkan.curency.config.CbrClientConfig;
import com.laurkan.curency.dto.CbrRateListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class CbrService {
    private final Cache<LocalDate, Map<String, Double>> cache;
    private final RestTemplate restClient;
    private final Gson gson;
    private final CbrClientConfig cbrClientConfig;

    @Autowired
    public CbrService(CbrClientConfig cbrClientConfig) {
        cache = CacheBuilder.newBuilder().build();
        restClient = new RestTemplate();
        gson = new Gson();
        this.cbrClientConfig = cbrClientConfig;
    }


    public Double requestByCurrencyCode(String code) {
        try {
            return cache.get(LocalDate.now(), this::readActualRates).get(code.toUpperCase());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Double> readActualRates() {
        ResponseEntity<String> response = restClient.getForEntity(cbrClientConfig.getUrl(), String.class);
        CbrRateListResponse cbrRateListResponse = gson.fromJson(response.getBody(), CbrRateListResponse.class);
        return cbrRateListResponse.getValute()
                .values().stream()
                .collect(Collectors
                        .toMap(CbrRateListResponse.ValuteResponse::getCharCode,
                                CbrRateListResponse.ValuteResponse::getValue));
    }

}
