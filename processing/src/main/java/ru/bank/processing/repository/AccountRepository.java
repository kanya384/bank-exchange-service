package ru.bank.processing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bank.processing.entity.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);
}
