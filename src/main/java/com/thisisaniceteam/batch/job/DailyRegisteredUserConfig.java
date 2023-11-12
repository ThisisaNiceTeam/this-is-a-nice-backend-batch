package com.thisisaniceteam.batch.job;

import com.thisisaniceteam.batch.item.writer.CountItemWriter;
import com.thisisaniceteam.batch.tasklet.DailyRegisteredFileTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class DailyRegisteredUserConfig {

    @Bean("dailyRegisteredUserJob")
    public Job getDailyRegisteredUserJob(JobRepository repository,
            @Qualifier("dailyRegisteredUserStep") Step aggregateStep,
            @Qualifier("registeredUserFileWriteStep") Step fileWriteStep) {
        return new JobBuilder("dailyRegisteredUserJob", repository)
                .start(aggregateStep)
                .next(fileWriteStep)
                .build();
    }

    @Bean("dailyRegisteredUserStep")
    @JobScope
    public Step getDailyRegisteredUserStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("dailyRegisteredUserReader") ItemReader itemReader,
            CountItemWriter itemWriter) {

        return new StepBuilder("dailyRegisteredUserStep", jobRepository)
                .<String, String>chunk(10, transactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }

    @Bean("registeredUserFileWriteStep")
    @JobScope
    public Step fileWrite(JobRepository repository,
            PlatformTransactionManager transactionManager,
            DailyRegisteredFileTasklet tasklet) {

        return new StepBuilder("registeredUserFileWriteStep", repository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

}
