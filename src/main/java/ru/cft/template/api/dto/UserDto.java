package ru.cft.template.api.dto;

import lombok.NoArgsConstructor;

import java.time.LocalDate;

public record UserDto(
        String lastName,
        String firstName,
        String middleName,
        String phone,
        String email,
        LocalDate birthdate
) {
}
