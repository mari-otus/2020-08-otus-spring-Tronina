package ru.otus.spring.listener.notification;

import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Booking;

/**
 * @author MTronina
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.notify.enabled", havingValue = "true")
public class NotifyHibernateEventListener implements PostInsertEventListener, PostUpdateEventListener,
        PostDeleteEventListener {

    private final NotificationManager notificationManager;

    @Override
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof Booking) {
            notificationManager.notifyEvent((Booking) event.getEntity());
        }
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof Booking) {
            notificationManager.notifyEvent((Booking) event.getEntity());
        }
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        notificationManager.notifyEvent((Booking) event.getEntity());
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }

}
