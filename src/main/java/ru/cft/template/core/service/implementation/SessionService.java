package ru.cft.template.core.service.implementation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.cft.template.api.dto.*;
import ru.cft.template.api.mapper.SessionMapper;
import ru.cft.template.core.exception.NotFoundException;
import ru.cft.template.core.exception.UnauthorizedException;
import ru.cft.template.core.model.Session;
import ru.cft.template.core.model.User;
import ru.cft.template.core.repository.SessionRepository;
import ru.cft.template.core.repository.UserRepository;
import ru.cft.template.core.service.ISessionService;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class SessionService implements ISessionService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public User authenticate(SessionCreateDto sessionCreateDto) {
        return userRepository.findById(Long.valueOf(sessionCreateDto.userId())).orElseThrow(
                () -> new NotFoundException("Пользователь " + sessionCreateDto.userId() + " не найден")
        );
    }

    public Session createSession(User user)
    {
        Session session = new Session(
                UUID.randomUUID(),
                user,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                LocalDateTime.now().plusMinutes(10)
        );

        return sessionRepository.save(session);
    }

    public SessionDto getSessionDtoById(UUID id) {
        Session session = sessionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Сессия не найдена")
        );

        if (!session.getExpirationTime().isAfter(LocalDateTime.now()))
            session.setActive(false);

        sessionRepository.save(session);
        return SessionMapper.toDto(session);
    }
    public boolean isActiveById(UUID id) {
        Session session = sessionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Сессия не найдена")
        );

        if (session.getExpirationTime().isAfter(LocalDateTime.now()))
            return true;

        session.setActive(false);
        sessionRepository.save(session);
        return false;
    }

    public Long getUserIdFromSession(String sessionId) {
        UUID sessionUUID = UUID.fromString(sessionId);
        Session session = sessionRepository.findByIdAndActiveIsTrue(sessionUUID)
                .filter(s -> s.getExpirationTime().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new UnauthorizedException("Сессия недействительна"));

        return session.getUser().getId();
    }

    public User getUserFromSession(String sessionId) {
        UUID sessionUUID = UUID.fromString(sessionId);
        Session session = sessionRepository.findByIdAndActiveIsTrue(sessionUUID)
                .filter(s -> s.getExpirationTime().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new UnauthorizedException("Сессия недействительна"));

        return session.getUser();
    }

    public void logout(UUID sessionId) {
        Session session = sessionRepository.findByIdAndActiveIsTrue(sessionId)
                .filter(s -> s.getExpirationTime().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new UnauthorizedException("Сессия недействительна"));

        session.setActive(false);
        sessionRepository.save(session);
    }


}
