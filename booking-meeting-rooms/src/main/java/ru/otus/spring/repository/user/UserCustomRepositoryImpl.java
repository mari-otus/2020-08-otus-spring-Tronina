package ru.otus.spring.repository.user;

import ru.otus.spring.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

/**
 * @author MTronina
 */
public class UserCustomRepositoryImpl implements UserCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void changePassword(Long userId, String password) {
        Query query = em.createQuery("update User u set u.password = :password " +
                "where u.id = :id");
        query.setParameter("id", userId);
        query.setParameter("password", password);
        query.executeUpdate();
    }
}
