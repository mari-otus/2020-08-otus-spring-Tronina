package ru.otus.spring.repository.user;

/**
 * Репозиторий для работы с пользователями.
 *
 * @author MTronina
 */
public interface UserCustomRepository {

    void changePassword(Long userId, String password);
}
