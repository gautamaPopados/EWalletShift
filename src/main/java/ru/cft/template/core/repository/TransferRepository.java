package ru.cft.template.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.template.api.enums.Status;
import ru.cft.template.api.enums.TransferType;
import ru.cft.template.core.model.Transfer;
import ru.cft.template.core.model.Wallet;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findByRecipientWalletAndStatus(Wallet wallet, Status status);
}
