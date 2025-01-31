package ru.cft.template.core.service;

import ru.cft.template.api.dto.*;
import ru.cft.template.core.model.User;

public interface IUserService {
    UserIdResponse createUser(UserCreateDto userDto);
    UserDto getUserDtoById(Long id);
    User getUserById(Long id);
    String encodePassword(String password);
    void updateUser(Long userId, String sessionId, UserPatchDto userPatchDto);
    }
