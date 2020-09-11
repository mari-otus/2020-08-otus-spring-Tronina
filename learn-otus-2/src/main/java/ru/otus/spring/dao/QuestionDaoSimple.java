package ru.otus.spring.dao;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * DAO для работы с вопросами для теста.
 *
 * @author Mariya Tronina
 */
@Data
@RequiredArgsConstructor
@Repository
public class QuestionDaoSimple implements QuestionDao {

    private final Resource resource;
    private final CsvMapper csvMapper;

    @Override
    public List<Question> findAllQuestion() {
        try {
            CsvSchema schema = csvMapper.schemaFor(Question.class)
                                              .withColumnSeparator(';');

            MappingIterator<Question> personIter = csvMapper
                    .reader(Question.class)
                    .with(schema)
                    .readValues(resource.getFile());
            return personIter.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
