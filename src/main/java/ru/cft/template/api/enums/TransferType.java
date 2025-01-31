package ru.cft.template.api.enums;

import java.util.Arrays;

public enum TransferType {
    IN,
    OUT;

    public static boolean isValid(String value) {
        return Arrays.stream(values())
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }
}
