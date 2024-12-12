package ru.bank.processing.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AccountResponseDTO {
    private Long id;

    private Long userId;

    private String currencyCode;

    private Double balance;
}
