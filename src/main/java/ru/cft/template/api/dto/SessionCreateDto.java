package ru.cft.template.api.dto;

public record SessionCreateDto(
        String userId,
        String password
) {
}
