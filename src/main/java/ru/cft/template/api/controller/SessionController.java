package ru.cft.template.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.dto.*;
import ru.cft.template.api.mapper.SessionMapper;
import ru.cft.template.core.model.Session;
import ru.cft.template.core.model.User;
import ru.cft.template.core.security.JwtTokenProvider;
import ru.cft.template.core.service.implementation.SessionService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("")
    public SessionDto createSession(@RequestBody SessionCreateDto sessionCreateDto) {
        User authenticatedUser = sessionService.authenticate(sessionCreateDto);

        String token = jwtTokenProvider.generateToken(authenticatedUser);

        Session session = sessionService.createSession(authenticatedUser, token);
        SessionDto sessionDto = SessionMapper.toDto(session);

        return sessionDto;
    }

    @GetMapping("/{id}")
    public SessionDto getById(@PathVariable UUID id) {
        return sessionService.getById(id);
    }
}
