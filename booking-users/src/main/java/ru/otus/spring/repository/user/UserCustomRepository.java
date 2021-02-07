package ru.otus.spring.repository.user;

/**
 * Репозиторий для работы с пользователями.
 *
 * @author Mariya Tronina
 */
public interface UserCustomRepository {

    void changePassword(Long userId, String password);
}