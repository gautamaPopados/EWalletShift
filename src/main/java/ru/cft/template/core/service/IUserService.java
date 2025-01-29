package ru.cft.template.core.service;

import ru.cft.template.api.dto.*;
import ru.cft.template.core.model.Session;
import ru.cft.template.core.model.User;

import java.util.UUID;

public interface IUserService {
    UserIdResponse createUser(UserCreateDto userDto);
    UserDto getById(Long id);
    String encodePassword(String password);
    void updateUser(Long userId, UserPatchDto userPatchDto);
    }
