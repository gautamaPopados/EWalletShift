package ru.cft.template.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.dto.WalletDto;
import ru.cft.template.core.exception.UnauthorizedException;
import ru.cft.template.core.service.implementation.WalletService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/{userId}")
    public WalletDto getWalletById(
            @PathVariable Long userId,
            @RequestHeader(value = "Authorization", required = false) String sessionId) {

        if (sessionId == null) {
            throw new UnauthorizedException("Нет авторизации");
        }


        return walletService.getWalletByUserId(userId, sessionId);
    }

}
