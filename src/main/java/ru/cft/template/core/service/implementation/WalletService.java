package ru.cft.template.core.service.implementation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.template.api.dto.*;
import ru.cft.template.core.exception.NotFoundException;
import ru.cft.template.core.model.User;
import ru.cft.template.core.model.Wallet;
import ru.cft.template.core.repository.UserRepository;
import ru.cft.template.core.repository.WalletRepository;
import ru.cft.template.core.service.IWalletService;

@Slf4j
@AllArgsConstructor
@Service
public class WalletService implements IWalletService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Override
    public WalletDto getWalletByUserId(Long userId, String sessionId) {
        User user =  userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователя " + userId + " не найдено"));

        Wallet wallet = user.getWallet();

        return WalletDto.builder().balance(wallet.getBalance()).number(wallet.getId()).build();
    }

    public Wallet createWallet()
    {
        return walletRepository.save(new Wallet());
    }

}
