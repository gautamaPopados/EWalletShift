package ru.cft.template.core.service.implementation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.template.api.dto.*;
import ru.cft.template.api.mapper.UserMapper;
import ru.cft.template.core.exception.NotFoundException;
import ru.cft.template.core.exception.UnauthorizedException;
import ru.cft.template.core.model.Session;
import ru.cft.template.core.model.User;
import ru.cft.template.core.repository.SessionRepository;
import ru.cft.template.core.repository.UserRepository;
import ru.cft.template.core.service.IUserService;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserIdResponse createUser(UserCreateDto userDto) {
        String hashedPassword = encodePassword(userDto.password());
        User user = UserMapper.toEntity(userDto);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return UserMapper.toIdResponse(user);
    }
    public UserDto getById(Long id) {
        var user =  userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователя " + id + " не найдено"));

        return UserMapper.toDto(user);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void updateUser(Long userId, String sessionId, UserPatchDto userPatchDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Пользователя " + userId + " не найдено")
        );

        if(!sessionService.isActiveById(UUID.fromString(sessionId))) {
            throw new UnauthorizedException("Сессия недействительна");
        }
        if(!sessionService.getUserId(UUID.fromString(sessionId)).equals(user.getId())) {
            throw new UnauthorizedException("Нельзя редактировать чужой профиль");
        }

        user.setFirstName(userPatchDto.firstName());
        user.setLastName(userPatchDto.lastName());
        user.setMiddleName(userPatchDto.middleName());
        user.setBirthdate(userPatchDto.birthdate());

        userRepository.save(user);
    }
}
