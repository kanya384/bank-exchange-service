package ru.bank.processing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExchangeMoneyDTO {
    @NotNull
    private Long from;
    @NotNull
    private Long to;
    @NotNull
    private Double amount;
}
