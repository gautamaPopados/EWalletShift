package ru.cft.template.api.dto;

import lombok.Builder;
import ru.cft.template.api.enums.Status;
import ru.cft.template.api.enums.TransferType;

import java.time.LocalDateTime;

@Builder
public record TransferDto(
        Long transferId,
        LocalDateTime creationTime,
        int amount,
        TransferType transferType,
        Status status)
{
}
