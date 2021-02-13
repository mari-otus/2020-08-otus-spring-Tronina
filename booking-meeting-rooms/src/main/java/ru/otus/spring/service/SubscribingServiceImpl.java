package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Room;
import ru.otus.spring.domain.Subscribing;
import ru.otus.spring.repository.SubscribingRepository;
import ru.otus.spring.security.AuthUserDetails;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class SubscribingServiceImpl implements SubscribingService {

    private final SubscribingRepository subscribingRepository;

    @Override
    public void subscribeRoom(Long roomId, AuthUserDetails userDetails) {
        Subscribing subscribing = subscribingRepository.findByLoginEqualsAndRoomId(userDetails.getUsername(), roomId)
                .orElseGet(() -> Subscribing.builder()
                            .login(userDetails.getUsername())
                            .room(Room.builder()
                                    .id(roomId)
                                    .build())
                            .build()
                );
        subscribing.setDeleteDate(null);
        subscribingRepository.save(subscribing);
    }

    @Override
    public void unsubscribeRoom(Long roomId, AuthUserDetails userDetails) {
        subscribingRepository.findByLoginEqualsAndRoomId(userDetails.getUsername(), roomId)
                .ifPresent(subscribing -> {
                    subscribing.setDeleteDate(LocalDateTime.now());
                    subscribingRepository.save(subscribing);
                });
    }

    @PostFilter("filterObject.login == authentication.name")
    @Override
    public List<Subscribing> getSubscribeRoom() {
        return subscribingRepository.findAllByDeleteDateIsNull();
    }
}
