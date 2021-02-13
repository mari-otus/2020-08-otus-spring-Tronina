package ru.otus.spring.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.domain.Subscribing;
import ru.otus.spring.security.AuthUserDetails;
import ru.otus.spring.service.SubscribingService;

import java.security.Principal;
import java.util.List;

/**
 * @author MTronina
 */
@Api(tags = "Сервис подписки на переговороки")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
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

    @GetMapping("/subscribing/rooms")
    public ResponseEntity<List<Subscribing>> getSubscribeRoom() {
        List<Subscribing> subscribeRoom = subscribingService.getSubscribeRoom();
        return ResponseEntity.ok(subscribeRoom);
    }

}
