package ru.otus.spring.listener.notification;

import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * @author MTronina
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.notify.enabled", havingValue = "true")
public class NotifyHibernateListenerConfig {

    @PersistenceUnit
    private final EntityManagerFactory emf;

    private final NotifyHibernateEventListener notifyHibernateEventListener;

    @PostConstruct
    public void init() {
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry eventListenerRegistry = sessionFactory.getServiceRegistry()
                .getService(EventListenerRegistry.class);

        eventListenerRegistry.getEventListenerGroup(EventType.POST_INSERT)
                .appendListeners(notifyHibernateEventListener);
        eventListenerRegistry.getEventListenerGroup(EventType.POST_UPDATE)
                .appendListeners(notifyHibernateEventListener);
    }
}
