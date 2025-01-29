package ru.cft.template.api.dto;

import jakarta.validation.constraints.*;
import ru.cft.template.core.validator.UniqueEmail;
import ru.cft.template.core.validator.UniquePhone;

import java.time.LocalDate;

public record UserPatchDto(
        @NotBlank(message = "Фамилия обязательна")
        @Pattern(
                regexp = "^[А-ЯЁ][а-яё]{1,49}$",
                message = "Фамилия должна начинаться с заглавной буквы и содержать только буквы русского алфавита (до 50 символов)"
        )
        String lastName,

        @NotBlank(message = "Имя обязательно")
        @Pattern(
                regexp = "^[А-ЯЁ][а-яё]{1,49}$",
                message = "Имя должно начинаться с заглавной буквы и содержать только буквы русского алфавита (до 50 символов)"
        )
        String firstName,

        @Pattern(
                regexp = "^[А-ЯЁ][а-яё]{1,49}$",
                message = "Отчество должно начинаться с заглавной буквы и содержать только буквы русского алфавита (до 50 символов)"
        )
        String middleName,

        @NotNull(message = "Дата рождения обязательна. Дата рождения в формате ISO 8601 (YYYY-MM-DD)")
        @Past(message = "Дата рождения не должна быть в будущем")
        LocalDate birthdate
) {
}
