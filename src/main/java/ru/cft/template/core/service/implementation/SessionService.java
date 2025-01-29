package ru.cft.template.core.service.implementation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.cft.template.api.dto.*;
import ru.cft.template.api.mapper.SessionMapper;
import ru.cft.template.api.mapper.UserMapper;
import ru.cft.template.core.exception.NotFoundException;
import ru.cft.template.core.exception.UnauthorizedException;
import ru.cft.template.core.model.Session;
import ru.cft.template.core.model.User;
import ru.cft.template.core.repository.SessionRepository;
import ru.cft.template.core.repository.UserRepository;
import ru.cft.template.core.security.JwtTokenProvider;
import ru.cft.template.core.service.ISessionService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class SessionService implements ISessionService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public User authenticate(SessionCreateDto sessionCreateDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        sessionCreateDto.userId(),
                        sessionCreateDto.password()
                )
        );

        return userRepository.findById(Long.valueOf(sessionCreateDto.userId())).orElseThrow(
                () -> new NotFoundException("User with id " + sessionCreateDto.userId() + " not found")
        );
    }

    public Session createSession(User user, String token)
    {
        Session session = new Session(
                UUID.randomUUID(),
                user,
                token,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                LocalDateTime.now().plus(jwtTokenProvider.getExpirationTime(), ChronoUnit.MILLIS)
        );

        return sessionRepository.save(session);
    }

    public SessionDto getById(UUID id) {
        Session session = sessionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Session not found")
        );
        session.setActive(!jwtTokenProvider.isTokenExpired(session.getToken()));

        return SessionMapper.toDto(session);
    }


}
