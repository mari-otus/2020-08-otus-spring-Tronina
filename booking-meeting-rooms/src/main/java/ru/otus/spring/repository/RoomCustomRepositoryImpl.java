package ru.otus.spring.repository;

import ru.otus.spring.domain.Room;
import ru.otus.spring.dto.RoomFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author MTronina
 */
public class RoomCustomRepositoryImpl implements RoomCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Room> findAllByFilter(RoomFilter filter) {
        Query query = em.createQuery("select r from Room r " +
                        "where r.deleteDate is null " +
                        (filter.getCapacity() != null ? "and  r.capacity = :capacity " : "") +
                        (filter.getRoomName() != null ? "and r.roomName = :roomName " : "") +
                        (filter.getHasVideoconference() != null ? "and r.hasVideoconference = :hasVideoconference " : "") +
                        (filter.getHasConditioner() != null ? "and r.hasConditioner = :hasConditioner " : ""),
                Room.class);
        if (filter.getCapacity() != null) {
            query.setParameter("capacity", filter.getCapacity());
        }
        if (filter.getRoomName() != null) {
            query.setParameter("roomName", filter.getRoomName());
        }
        if (filter.getHasVideoconference() != null) {
            query.setParameter("hasVideoconference", filter.getHasVideoconference());
        }
        if (filter.getHasConditioner() != null) {
            query.setParameter("hasConditioner", filter.getHasConditioner());
        }
        return query.getResultList();
    }
}
