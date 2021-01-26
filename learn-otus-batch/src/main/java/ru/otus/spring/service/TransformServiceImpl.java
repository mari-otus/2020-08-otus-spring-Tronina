package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.source.AuthorSource;
import ru.otus.spring.model.source.BookSource;
import ru.otus.spring.model.source.GenreSource;
import ru.otus.spring.model.targets.AuthorTarget;
import ru.otus.spring.model.targets.BookTarget;
import ru.otus.spring.model.targets.GenreTarget;

import java.util.ArrayList;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class TransformServiceImpl implements TransformService {

    private final MongoTemplate mongoTemplate;

    @Override
    public AuthorTarget toAuthorModel(AuthorSource authorSource) {
        AuthorTarget authorTarget = new AuthorTarget();
        BeanUtils.copyProperties(authorSource, authorTarget, "id");
        authorTarget.setSourceId(authorSource.getId());
        return authorTarget;
    }

    @Override
    public GenreTarget toGenreModel(GenreSource genreSource) {
        GenreTarget genreTarget = new GenreTarget();
        BeanUtils.copyProperties(genreSource, genreTarget, "id");
        genreTarget.setSourceId(genreSource.getId());
        return genreTarget;
    }

    @Override
    public BookTarget toBookModel(BookSource bookSource) {
        BookTarget bookTarget = new BookTarget();
        BeanUtils.copyProperties(bookSource, bookTarget);
        bookTarget.setAuthors(new ArrayList<>());
        bookTarget.setGenres(new ArrayList<>());
        bookSource.getAuthors().forEach(author -> {
            bookTarget.getAuthors().add(mongoTemplate
                    .findOne(Query.query(Criteria.where("sourceId").is(author.getId())), AuthorTarget.class));
        });
        bookSource.getGenres().forEach(genre -> {
            bookTarget.getGenres()
                    .add(mongoTemplate.findOne(Query.query(Criteria.where("sourceId").is(genre.getId())), GenreTarget.class));
        });
        return bookTarget;
    }
}
