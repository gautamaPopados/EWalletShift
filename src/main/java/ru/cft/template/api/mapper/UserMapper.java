package ru.cft.template.api.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.api.dto.UserCreateDto;
import ru.cft.template.api.dto.UserDto;
import ru.cft.template.api.dto.UserIdResponse;
import ru.cft.template.core.model.User;

@Component
public class UserMapper {
    public static User toEntity(UserCreateDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setLastName(dto.lastName());
        user.setFirstName(dto.firstName());
        user.setMiddleName(dto.middleName());
        user.setPhone(dto.phone());
        user.setEmail(dto.email());
        user.setBirthdate(dto.birthdate());
        user.setPassword(dto.password());
        return user;
    }

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getLastName(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getPhone(),
                user.getEmail(),
                user.getBirthdate()
        );
    }

    public static UserIdResponse toIdResponse(User user) {
        if (user == null) {
            return null;
        }
        UserIdResponse response = new UserIdResponse(user.getId());
        return response;
    }
}