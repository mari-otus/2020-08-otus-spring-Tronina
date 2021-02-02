package ru.otus.spring;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.model.source.BookSource;
import ru.otus.spring.model.targets.BookTarget;
import ru.otus.spring.repository.source.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.spring.config.JobConfig.MIGRATION_LIBRARY_JOB;

/**
 * @author MTronina
 */
@SpringBootTest
@SpringBatchTest
@DisplayName("migrationLibraryJob должен")
public class MigrationLibraryJobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @SneakyThrows
    @Test
    @DisplayName("выполнить миграцию из реляционной БД в MongoDB")
    void testJob(){
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(MIGRATION_LIBRARY_JOB);

        List<BookSource> booksSource = bookRepository.findAll();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        List<BookTarget> booksDest = mongoTemplate.findAll(BookTarget.class);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
        assertThat(booksDest)
                .hasSize(booksSource.size())
                .allMatch(bookDest ->
                                booksSource.stream()
                                        .anyMatch(bookSource ->
                                            bookSource.getName().equals(bookDest.getName()) &&
                                                    bookSource.getAuthors().size() == bookDest.getAuthors().size() &&
                                                    bookSource.getGenres().size() == bookDest.getGenres().size()
                                        ));
    }
}
