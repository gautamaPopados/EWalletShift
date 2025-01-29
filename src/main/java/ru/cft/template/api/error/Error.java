package ru.cft.template.api.error;

import lombok.Builder;

@Builder
public record Error(
        int code,
        String message
) {
}