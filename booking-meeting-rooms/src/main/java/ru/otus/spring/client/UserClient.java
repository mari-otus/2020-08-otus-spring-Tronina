package ru.otus.spring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.spring.dto.UserDto;

/**
 * @author MTronina
 */
@FeignClient(name = "users", url = "${integration.users.url}")
public interface UserClient {

    @GetMapping("/users")
    ResponseEntity<Page<UserDto>> getUsers(Pageable pageable);

    @GetMapping("/users/{login}")
    ResponseEntity<UserDto> getUserByLogin(@PathVariable String login);

    @PostMapping("/users")
    ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto);

    @PutMapping("/users")
    ResponseEntity<UserDto> editUser(@RequestBody UserDto userDto);

    @PutMapping("/users/{userId}/lock")
    ResponseEntity<UserDto> lockUser(@PathVariable Long userId);

    @PutMapping("/users/{userId}/unlock")
    ResponseEntity<UserDto> unlockUser(@PathVariable Long userId);

    @PutMapping("/users/{userId}/enable")
    ResponseEntity<UserDto> enableUser(@PathVariable Long userId);

    @PutMapping("/users/{userId}/disable")
    ResponseEntity<UserDto> disableUser(@PathVariable Long userId);

}
