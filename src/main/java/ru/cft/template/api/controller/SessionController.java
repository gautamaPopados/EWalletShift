package ru.cft.template.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.dto.*;
import ru.cft.template.api.mapper.SessionMapper;
import ru.cft.template.core.model.Session;
import ru.cft.template.core.model.User;
import ru.cft.template.core.service.implementation.SessionService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping()
    public SessionDto createSession(@RequestBody SessionCreateDto sessionCreateDto) {
        User authenticatedUser = sessionService.authenticate(sessionCreateDto);
        Session session = sessionService.createSession(authenticatedUser);

        return SessionMapper.toDto(session);
    }

    @GetMapping("/{id}")
    public SessionDto getById(@PathVariable UUID id) {
        return sessionService.getSessionDtoById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> logout(@PathVariable UUID id) {
        sessionService.logout(id);
        return ResponseEntity.ok().build();
    }
}
