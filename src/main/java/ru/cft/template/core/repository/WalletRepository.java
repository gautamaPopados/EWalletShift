package ru.cft.template.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.template.core.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
