package ru.otus.spring.service;

import ru.otus.spring.security.AuthUserDetails;

/**
 * @author MTronina
 */
public interface SubscribingService {

    void subscribeRoom(Long roomId, AuthUserDetails userDetails);

    void unsubscribeRoom(Long roomId, AuthUserDetails userDetails);
}
