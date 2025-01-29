package ru.cft.template.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionCreateDto(
        String userId,
        String password
) {
}
