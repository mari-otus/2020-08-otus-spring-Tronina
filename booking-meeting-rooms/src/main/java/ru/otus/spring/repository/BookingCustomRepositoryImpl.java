package ru.otus.spring.repository;

import org.apache.commons.lang3.ObjectUtils;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.dto.BookingFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author MTronina
 */
public class BookingCustomRepositoryImpl implements BookingCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Booking> findAllActiveByFilter(BookingFilter filter) {
        Query query = em.createQuery("select b from Booking b " +
                        "where b.deleteDate is null " +
                        (filter.getLogin() != null ? "and b.login = :login " : "") +
                        (filter.getRoomName() != null ? "and b.room.roomName = :roomName " : "") +
                        (filter.getStartBooking() != null
                         ? "and function('to_char', b.beginDate, 'YYYY-mm-dd HH24:MI')  between :startBeginDate and :startEndDate " : "") +
                        (filter.getEndBooking() != null
                         ? "and function('to_char', b.endDate, 'YYYY-mm-dd HH24:MI')  between :endBeginDate and :endEndDate " : "") ,
                Booking.class);
        if (filter.getLogin() != null) {
            query.setParameter("login", filter.getLogin());
        }
        if (filter.getRoomName() != null) {
            query.setParameter("roomName", filter.getRoomName());
        }
        if (filter.getStartBooking() != null) {
            if (filter.getStartBooking().getBeginPeriod() != null || filter.getStartBooking().getEndPeriod() != null) {
                query.setParameter("startBeginDate",
                        ObjectUtils.firstNonNull(filter.getStartBooking().getBeginPeriod(), filter.getStartBooking().getEndPeriod() ).format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                query.setParameter("startEndDate",
                        ObjectUtils.firstNonNull(filter.getStartBooking().getEndPeriod() , filter.getStartBooking().getBeginPeriod()).format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
        }
        if (filter.getEndBooking() != null) {
            if (filter.getEndBooking().getBeginPeriod() != null || filter.getEndBooking().getEndPeriod() != null) {
                query.setParameter("endBeginDate",
                        ObjectUtils.firstNonNull(filter.getEndBooking().getBeginPeriod(), filter.getEndBooking().getEndPeriod() ).format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                query.setParameter("endEndDate",
                        ObjectUtils.firstNonNull(filter.getEndBooking().getEndPeriod() , filter.getEndBooking().getBeginPeriod()).format(
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
        }
        return query.getResultList();
    }
}
