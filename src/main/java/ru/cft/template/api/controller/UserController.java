package ru.cft.template.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.dto.UserCreateDto;
import ru.cft.template.api.dto.UserDto;
import ru.cft.template.api.dto.UserIdResponse;
import ru.cft.template.api.dto.UserPatchDto;
import ru.cft.template.core.service.implementation.UserService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public UserIdResponse createUser(@RequestBody @Valid UserCreateDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable @NotNull Long userId,
            @RequestBody UserPatchDto userPatchDto,
            @RequestHeader(value = "Authorization", required = false) String sessionId) {

        userService.updateUser(userId, sessionId, userPatchDto);
        return ResponseEntity.ok().build();
    }
}
