package ru.otus.spring.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.security.AuthUserDetails;
import ru.otus.spring.service.UserService;

import java.security.Principal;

/**
 * @author MTronina
 */
@Api(tags = "Сервис работы с пользователями")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{login}")
    ResponseEntity<UserDto> getUserByLogin(@PathVariable String login) {
        UserDto user = userService.getUserByLogin(login);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserDto>> getUsers(Pageable pageable) {
        Page<UserDto> users = userService.getUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(
            @RequestBody UserDto userDto) {
        UserDto userNew = userService.createUser(userDto);
        return ResponseEntity.ok(userNew);
    }

    @PutMapping("/users")
    public ResponseEntity<UserDto> editUser(
            @RequestBody UserDto userDto) {
        userService.editUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{userId}/lock")
    public ResponseEntity<UserDto> lockUser(@PathVariable Long userId) {
        userService.lockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{userId}/unlock")
    public ResponseEntity<UserDto> unlockUser(@PathVariable Long userId) {
        userService.unlockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{userId}/enable")
    public ResponseEntity<UserDto> enableUser(@PathVariable Long userId) {
        userService.enableUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{userId}/disable")
    public ResponseEntity<UserDto> disableUser(@PathVariable Long userId) {
        userService.disableUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/account")
    public ResponseEntity<UserDto> getAccount(Principal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthUserDetails userDetails = (AuthUserDetails) authenticationToken.getPrincipal();
        UserDto user = userService.getUserByLogin(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

}
