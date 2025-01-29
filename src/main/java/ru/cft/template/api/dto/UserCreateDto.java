package ru.cft.template.api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.cft.template.core.validator.UniqueEmail;
import ru.cft.template.core.validator.UniquePhone;

import java.time.LocalDate;

public record UserCreateDto(
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

        @UniquePhone
        @NotBlank(message = "Номер телефона обязателен")
        @Pattern(
                regexp = "^7\\d{10}$",
                message = "Номер телефона должен содержать 11 цифр и начинаться с '7'"
        )
        String phone,

        @UniqueEmail
        @NotBlank(message = "Email обязателен")
        @Email(message = "Некорректный формат email")
        String email,

        @NotNull(message = "Дата рождения обязательна. Дата рождения в формате ISO 8601 (YYYY-MM-DD)")
        @Past(message = "Дата рождения не должна быть в будущем")
        LocalDate birthdate,

        @NotBlank(message = "Пароль обязателен")
        @Size(min = 8, max = 64, message = "Пароль от 8 до 64 символов.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!?\\.])[A-Za-z\\d!?\\.]{8,64}$",
                message = "Только латинские символы, цифры, знаки только !?. Обязательно наличие минимум 1 буквы верхнего и нижнего регистра, цифры и знака."
        )
        String password
) {
}
