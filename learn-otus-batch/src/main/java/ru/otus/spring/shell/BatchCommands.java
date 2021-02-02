package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import static ru.otus.spring.config.JobConfig.MIGRATION_LIBRARY_JOB;
import static ru.otus.spring.config.JobConfig.RESTART_DATE_JOB;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    private Long lastJobExecutionId;

    @ShellMethod(value = "startMigration", key = "sm")
    public void startMigration() throws Exception {
        lastJobExecutionId = jobOperator.start(MIGRATION_LIBRARY_JOB, null);
        System.out.println(jobOperator.getSummary(lastJobExecutionId));
    }

    @ShellMethod(value = "restartMigration", key = "rm")
    public void restartMigration() throws Exception {
        final Long restartJobExecutionId  = jobOperator.start(MIGRATION_LIBRARY_JOB,
                RESTART_DATE_JOB + "=" + LocalDateTime.now());
        System.out.println(MessageFormat.format("lastJobExecutionId = {0}, restartJobExecutionId = {1}",
                lastJobExecutionId, restartJobExecutionId));
    }

    @SneakyThrows
    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(MIGRATION_LIBRARY_JOB));
        System.out.println(jobExplorer.getJobInstanceCount(MIGRATION_LIBRARY_JOB));
    }
}
