package com.laurkan.curency.controller;

import com.laurkan.curency.service.CbrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CbrService cbrService;

    @RequestMapping("/rate/{code}")
    public Double readCurrencyByCode(@PathVariable String code) {
        return cbrService.requestByCurrencyCode(code);
    }
}
