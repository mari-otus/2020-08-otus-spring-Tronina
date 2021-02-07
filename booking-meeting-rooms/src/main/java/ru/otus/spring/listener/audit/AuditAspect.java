package ru.otus.spring.listener.audit;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Booking;

/**
 * @author MTronina
 */
@Aspect
@Component
@RequiredArgsConstructor
@ConditionalOnExpression("${app.audit.enabled} and ('${app.audit.listener}' == 'spring' or '${app.audit.listener}' == null)")
public class AuditAspect {

    private final AuditManager auditManager;

    @AfterReturning(
            pointcut = "execution(* ru.otus.spring.repository.BookingRepository.save(..))",
            returning = "booking"
    )
    public void auditAfterReturning(JoinPoint joinPoint, Booking booking) {
        auditManager.insertAuditEntry(booking);
    }
}
