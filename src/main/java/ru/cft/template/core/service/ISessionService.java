package ru.cft.template.core.service;

import ru.cft.template.api.dto.SessionCreateDto;
import ru.cft.template.api.dto.SessionDto;
import ru.cft.template.core.model.Session;
import ru.cft.template.core.model.User;

import java.util.UUID;

public interface ISessionService {
     User authenticate(SessionCreateDto sessionCreateDto);
     Session createSession(User user, String token);
     SessionDto getById(UUID id);
    }
