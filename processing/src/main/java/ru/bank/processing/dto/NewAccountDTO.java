package ru.bank.processing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewAccountDTO {
    @NotNull
    private String currency;
    @NotNull
    private Long user;
}
