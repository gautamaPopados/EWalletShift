package ru.cft.template.api.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.api.dto.SessionDto;
import ru.cft.template.core.model.Session;

@Component
public class SessionMapper {

    public static SessionDto toDto(Session session) {
        if (session == null) {
            return null;
        }
        return new SessionDto(
                session.getExpirationTime(),
                session.getId(),
                session.isActive()
        );
    }
}