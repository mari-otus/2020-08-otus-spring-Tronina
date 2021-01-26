package ru.otus.spring.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.model.source.AuthorSource;
import ru.otus.spring.model.source.BookSource;
import ru.otus.spring.model.source.GenreSource;
import ru.otus.spring.model.targets.AuthorTarget;
import ru.otus.spring.model.targets.BookTarget;
import ru.otus.spring.model.targets.GenreTarget;
import ru.otus.spring.repository.source.AuthorRepository;
import ru.otus.spring.repository.source.BookRepository;
import ru.otus.spring.repository.source.GenreRepository;
import ru.otus.spring.service.JobService;
import ru.otus.spring.service.TransformService;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfig {
    
    private static final int CHUNK_SIZE = 5;
    public static final String RESTART_DATE_JOB = "restartDateJob";
    public static final String MIGRATION_LIBRARY_JOB = "migrationLibraryJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobService jobService;

    @Bean
    public RepositoryItemReader<AuthorSource> readerAuthor(AuthorRepository authorRepository) {
        return new RepositoryItemReaderBuilder<AuthorSource>()
                .name("authorItemReader")
                .repository(authorRepository)
                .methodName("findAll")
                .pageSize(20)
                .sorts(Map.of())
                .build();
    }

    @Bean
    public RepositoryItemReader<GenreSource> readerGenre(GenreRepository genreRepository) {
        return new RepositoryItemReaderBuilder<GenreSource>()
                .name("genreItemReader")
                .repository(genreRepository)
                .methodName("findAll")
                .pageSize(20)
                .sorts(Map.of())
                .build();
    }

    @Bean
    public RepositoryItemReader<BookSource> readerBook(BookRepository bookRepository) {
        return new RepositoryItemReaderBuilder<BookSource>()
                .name("bookItemReader")
                .repository(bookRepository)
                .methodName("findAll")
                .pageSize(20)
                .sorts(Map.of())
                .build();
    }

    @Bean
    public ItemProcessor<AuthorSource, AuthorTarget> processorAuthor(TransformService transformService) {
        return transformService::toAuthorModel;
    }

    @Bean
    public ItemProcessor<GenreSource, GenreTarget> processorGenre(TransformService transformService) {
        return transformService::toGenreModel;
    }

    @Bean
    public ItemProcessor<BookSource, BookTarget> processorBook(TransformService transformService) {
        return transformService::toBookModel;
    }

    @Bean
    public MongoItemWriter<AuthorTarget> writerAuthor(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<AuthorTarget>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }

    @Bean
    public MongoItemWriter<GenreTarget> writerGenre(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<GenreTarget>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }

    @Bean
    public MongoItemWriter<BookTarget> writerBook(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<BookTarget>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }

    @Bean
    public Job migrationLibraryJob(@Qualifier("stepMigrationBook") Step stepMigrationBook,
                             @Qualifier("stepMigrationAuthor") Step stepMigrationAuthor,
                             @Qualifier("stepMigrationGenre") Step stepMigrationGenre) {
        return jobBuilderFactory.get(MIGRATION_LIBRARY_JOB)
                .incrementer(new RunIdIncrementer())
                .start(splitFlow(stepMigrationAuthor, stepMigrationGenre))
                .next(stepMigrationBook)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info("Начало job");
                        jobService.clearTarget();
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step stepMigrationBook(@Qualifier("writerBook") MongoItemWriter<BookTarget> writer,
                                  @Qualifier("readerBook") ItemReader<BookSource> reader,
                                  @Qualifier("processorBook") ItemProcessor<BookSource, BookTarget> processor) {
        return stepBuilderFactory.get("migrationBook")
                .<BookSource, BookTarget>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step stepMigrationAuthor(@Qualifier("writerAuthor") MongoItemWriter<AuthorTarget> writer,
                                    @Qualifier("readerAuthor") ItemReader<AuthorSource> reader,
                                    @Qualifier("processorAuthor") ItemProcessor<AuthorSource, AuthorTarget> processor) {
        return stepBuilderFactory.get("migrationAuthor")
                .<AuthorSource, AuthorTarget>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step stepMigrationGenre(@Qualifier("writerGenre") MongoItemWriter<GenreTarget> writer,
                                   @Qualifier("readerGenre") ItemReader<GenreSource> reader,
                                   @Qualifier("processorGenre") ItemProcessor<GenreSource, GenreTarget> processor) {
        return stepBuilderFactory.get("migrationGenre")
                .<GenreSource, GenreTarget>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Flow splitFlow(@Qualifier("stepMigrationAuthor") Step stepMigrationAuthor,
                          @Qualifier("stepMigrationGenre") Step stepMigrationGenre) {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(taskExecutor())
                .add(
                        new FlowBuilder<SimpleFlow>("flowAuthor")
                                .start(stepMigrationAuthor)
                                .build(),
                        new FlowBuilder<SimpleFlow>("flowGenre")
                                .start(stepMigrationGenre)
                                .build()
                )
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("migration_lib_batch");
    }
}
