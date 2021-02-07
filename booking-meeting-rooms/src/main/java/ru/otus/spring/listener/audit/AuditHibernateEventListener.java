package ru.otus.spring.listener.audit;

import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Booking;

/**
 * @author MTronina
 */
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${app.audit.enabled} and '${app.audit.listener}' == 'hibernate'")
public class AuditHibernateEventListener implements PostInsertEventListener, PostUpdateEventListener {

    private final AuditManager auditManager;

    @Override
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof Booking) {
            auditManager.insertAuditEntry((Booking) event.getEntity());
        }
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof Booking) {
            auditManager.insertAuditEntry((Booking) event.getEntity());
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }

}
