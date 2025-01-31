package ru.cft.template.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.dto.UserCreateDto;
import ru.cft.template.api.dto.UserDto;
import ru.cft.template.api.dto.UserIdResponse;
import ru.cft.template.api.dto.UserPatchDto;
import ru.cft.template.core.exception.UnauthorizedException;
import ru.cft.template.core.service.implementation.UserService;

import java.nio.file.AccessDeniedException;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public UserIdResponse createUser(@RequestBody @Valid UserCreateDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long userId,
            @RequestBody UserPatchDto userPatchDto,
            @RequestHeader(value = "Authorization", required = false) String sessionId) {

        if (sessionId == null) {
            throw new UnauthorizedException("Нет авторизации");
        }
        userService.updateUser(userId, sessionId, userPatchDto);
        return ResponseEntity.ok().build();
    }
}
