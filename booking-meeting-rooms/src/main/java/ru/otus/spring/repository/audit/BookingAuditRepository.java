package ru.otus.spring.repository.audit;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.audit.BookingAudit;

/**
 * @author MTronina
 */
public interface BookingAuditRepository extends MongoRepository<BookingAudit, String> {
}
