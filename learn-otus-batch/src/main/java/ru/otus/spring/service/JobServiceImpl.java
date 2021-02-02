package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.targets.AuthorTarget;
import ru.otus.spring.model.targets.BookTarget;
import ru.otus.spring.model.targets.GenreTarget;

/**
 * Сервис для работы с job.
 *
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final MongoTemplate mongoTemplate;

    @Override
    public void clearTarget() {
        mongoTemplate.dropCollection(AuthorTarget.class);
        mongoTemplate.dropCollection(GenreTarget.class);
        mongoTemplate.dropCollection(BookTarget.class);
    }
}
