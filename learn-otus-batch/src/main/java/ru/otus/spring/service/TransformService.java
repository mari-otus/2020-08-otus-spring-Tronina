package ru.otus.spring.service;

import ru.otus.spring.model.source.AuthorSource;
import ru.otus.spring.model.source.BookSource;
import ru.otus.spring.model.source.GenreSource;
import ru.otus.spring.model.targets.AuthorTarget;
import ru.otus.spring.model.targets.BookTarget;
import ru.otus.spring.model.targets.GenreTarget;

/**
 * Сервис трансформации данных.
 *
 * @author MTronina
 */
public interface TransformService {

    AuthorTarget toAuthorModel(AuthorSource authorSource);

    GenreTarget toGenreModel(GenreSource genreSource);

    BookTarget toBookModel(BookSource bookSource);
}
