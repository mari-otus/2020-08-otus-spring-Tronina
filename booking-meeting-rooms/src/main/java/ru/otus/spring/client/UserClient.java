package ru.otus.spring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.dto.UserDto;

/**
 * @author MTronina
 */
@FeignClient(name = "booking-users")
@RequestMapping("/users")
public interface UserClient {

    @GetMapping("/{login}")
    ResponseEntity<UserDto> getUserByLogin(@PathVariable String login);

}
