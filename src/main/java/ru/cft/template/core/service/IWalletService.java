package ru.cft.template.core.service;

import ru.cft.template.api.dto.WalletDto;

public interface IWalletService {
        WalletDto getWalletByUserId(Long userId, String  sessionId);

    }
