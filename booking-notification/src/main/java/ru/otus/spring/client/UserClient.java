package ru.otus.spring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.spring.model.UserDto;

/**
 * @author MTronina
 */
@FeignClient(name = "users", url = "${integration.users.url}")
public interface UserClient {

    @GetMapping("/users/{login}")
    ResponseEntity<UserDto> getUserByLogin(@PathVariable String login);

}
