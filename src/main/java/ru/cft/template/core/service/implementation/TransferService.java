package ru.cft.template.core.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cft.template.api.dto.TransferCreateDto;
import ru.cft.template.api.dto.TransferDto;
import ru.cft.template.api.enums.Status;
import ru.cft.template.api.enums.TransferType;
import ru.cft.template.core.exception.InvalidRequestException;
import ru.cft.template.core.exception.NotFoundException;
import ru.cft.template.core.exception.UnauthorizedException;
import ru.cft.template.core.model.Transfer;
import ru.cft.template.core.model.User;
import ru.cft.template.core.model.Wallet;
import ru.cft.template.core.repository.TransferRepository;
import ru.cft.template.core.repository.WalletRepository;
import ru.cft.template.core.service.ITransferService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TransferService implements ITransferService {
    private final TransferRepository transferRepository;
    private final SessionService sessionService;
    private final WalletRepository walletRepository;
    private final WalletService walletService;
    private final UserService userService;

    @Override
    public TransferDto createTransfer(TransferCreateDto transferCreateDto, String sessionId) {
        if(!sessionService.isActiveById(UUID.fromString(sessionId))) {
            throw new UnauthorizedException("Сессия недействительна");
        }
        int amount = Integer.parseInt(transferCreateDto.getAmount());
        Long walletId = Long.parseLong(transferCreateDto.getWalletId());

        if (amount < 0) {
            throw new InvalidRequestException("Неправильный запрос");
        }

        Wallet senderWallet = userService.getUserById(sessionService.getUserIdFromSession(sessionId)).getWallet() ;
        Wallet recipientWallet = walletService.getWalletById(walletId);

        Transfer transfer = new Transfer();
        transfer.setAmount(amount);
        transfer.setCreationTime(LocalDateTime.now());
        transfer.setSenderWallet(senderWallet);
        transfer.setRecipientWallet(recipientWallet);

        if (senderWallet.getBalance() < amount)
            transfer.setStatus(Status.UNPAID);
        else {
            transfer.setStatus(Status.PAID);
            senderWallet.setBalance(senderWallet.getBalance() - amount);
            recipientWallet.setBalance(recipientWallet.getBalance() + amount);
            walletRepository.save(senderWallet);
            walletRepository.save(recipientWallet);
        }
        Transfer savedTransfer = transferRepository.save(transfer);

        return TransferDto.builder().
                transferId(savedTransfer.getId()).
                transferType(TransferType.OUT).
                creationTime(savedTransfer.getCreationTime()).
                amount(savedTransfer.getAmount()).
                status(savedTransfer.getStatus()).build();
    }

    public TransferDto getTransferById(Long transferID, String sessionId) {
        if(!sessionService.isActiveById(UUID.fromString(sessionId))) {
            throw new UnauthorizedException("Сессия недействительна");
        }

        Transfer transfer = transferRepository.findById(transferID).orElseThrow(
                () -> new NotFoundException("Перевод не найден"));

        Wallet recipientWallet = transfer.getRecipientWallet() ;
        Wallet senderWallet = transfer.getSenderWallet() ;

        User user = sessionService.getUserFromSession(sessionId);

        if(!(user.getWallet().equals(recipientWallet) || user.getWallet().equals(senderWallet))) {
            throw new UnauthorizedException("У вас нет доступа к этому переводу");
        }

        return convertToDto(transfer);
    }

    public List<TransferDto> getTransfers(TransferType transferType, Status status, String userId, String sessionId) {
        if(!sessionService.isActiveById(UUID.fromString(sessionId))) {
            throw new UnauthorizedException("Сессия недействительна");
        }

        Wallet recipientWallet = userService.getUserById(Long.parseLong(userId)).getWallet() ;
        return transferRepository.findByRecipientWalletAndStatus(recipientWallet, status)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private TransferDto convertToDto(Transfer transfer) {
        if (transfer == null) {
            return null;
        }

        return new TransferDto(
                transfer.getId(),
                transfer.getCreationTime(),
                transfer.getAmount(),
                TransferType.OUT,
                transfer.getStatus()
        );
    }
}
