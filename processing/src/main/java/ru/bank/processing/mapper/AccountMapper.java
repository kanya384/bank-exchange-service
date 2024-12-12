package ru.bank.processing.mapper;

import org.mapstruct.*;
import ru.bank.processing.dto.AccountResponseDTO;
import ru.bank.processing.dto.NewAccountDTO;
import ru.bank.processing.entity.Account;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class AccountMapper {
    @BeforeMapping
    protected void accountMapping(NewAccountDTO accountDTO, @MappingTarget Account account) {
        account.setBalance(0.0);
    }
    @Mapping(source = "currency", target = "currencyCode")
    @Mapping(source = "user", target = "userId")
    public abstract Account map(NewAccountDTO dto);
    public abstract AccountResponseDTO map(Account account);
}
