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

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UserPatchDto userPatchDto,
            @AuthenticationPrincipal UserDetails currentUser) {

        Long currentUserId = Long.valueOf(currentUser.getUsername());

        if (!currentUserId.equals(id)) {
            throw new UnauthorizedException("Вы не можете редактировать чужой профиль");
        }

        userService.updateUser(id, userPatchDto);
        return ResponseEntity.ok("Профиль успешно обновлен");
    }
}
