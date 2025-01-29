package ru.cft.template.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record SessionDto(
        LocalDateTime expirationTime,
        UUID sessionId,
        Boolean active
) {
}
