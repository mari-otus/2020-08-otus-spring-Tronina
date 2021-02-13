package ru.otus.spring.service;

import ru.otus.spring.domain.Subscribing;
import ru.otus.spring.security.AuthUserDetails;

import java.util.List;

/**
 * @author MTronina
 */
public interface SubscribingService {

    void subscribeRoom(Long roomId, AuthUserDetails userDetails);

    void unsubscribeRoom(Long roomId, AuthUserDetails userDetails);

    List<Subscribing> getSubscribeRoom();
}
