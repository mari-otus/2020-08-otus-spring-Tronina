package ru.otus.spring.listener.audit;

import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * @author MTronina
 */
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${app.audit.enabled} and '${app.audit.listener}' == 'hibernate'")
public class AuditHibernateListenerConfig {

    @PersistenceUnit
    private final EntityManagerFactory emf;

    private final AuditHibernateEventListener auditHibernateEventListener;

    @PostConstruct
    public void init() {
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry eventListenerRegistry = sessionFactory.getServiceRegistry()
                .getService(EventListenerRegistry.class);

        eventListenerRegistry.getEventListenerGroup(EventType.POST_INSERT)
                .appendListeners(auditHibernateEventListener);
        eventListenerRegistry.getEventListenerGroup(EventType.POST_UPDATE)
                .appendListeners(auditHibernateEventListener);
    }
}
