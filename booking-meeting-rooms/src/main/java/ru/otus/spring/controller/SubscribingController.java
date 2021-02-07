package ru.otus.spring.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.security.AuthUserDetails;
import ru.otus.spring.service.SubscribingService;

import java.security.Principal;

/**
 * @author MTronina
 */
@Api(tags = "Сервис подписки на переговороки")
@RestController
@RequiredArgsConstructor
public class SubscribingController {

    private final SubscribingService subscribingService;

    @PostMapping("/subscribing/rooms/{roomId}")
    public ResponseEntity<Void> subscribeRoom(
            @PathVariable Long roomId, Principal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthUserDetails userDetails = (AuthUserDetails) authenticationToken.getPrincipal();
        subscribingService.subscribeRoom(roomId, userDetails);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/subscribing/rooms/{roomId}")
    public ResponseEntity<Void> unsubscribeRoom(
            @PathVariable Long roomId, Principal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthUserDetails userDetails = (AuthUserDetails) authenticationToken.getPrincipal();
        subscribingService.unsubscribeRoom(roomId, userDetails);
        return ResponseEntity.ok().build();
    }

}
