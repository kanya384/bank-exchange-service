package ru.bank.processing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PutAccountMoneyDTO {
    @NotNull
    private String uid;
    @NotNull
    private Double money;
}
