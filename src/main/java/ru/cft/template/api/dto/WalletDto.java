package ru.cft.template.api.dto;

import lombok.Builder;

@Builder
public record WalletDto(
        Long number,
        int balance) {
}
