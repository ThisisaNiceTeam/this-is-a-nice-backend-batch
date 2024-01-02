package com.thisisaniceteam.batch.job;

import com.thisisaniceteam.batch.item.writer.CountItemWriter;
import com.thisisaniceteam.batch.tasklet.WeeklyActiveUserFileTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class WeeklyActivateUserConfig {

    @Bean("weeklyActiveUserJob")
    public Job getWeeklyActiveUserJob(JobRepository repository,
            @Qualifier("weeklyActiveUserStep") Step aggregateStep,
            @Qualifier("weeklyActiveUserFileWriteStep") Step fileWriteStep) {
        return new JobBuilder("weeklyActiveUserJob", repository)
                .start(aggregateStep)
                .next(fileWriteStep)
                .build();
    }

    @Bean("weeklyActiveUserStep")
    @JobScope
    public Step getDailyTransmissionStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("weeklyActivateUserReader") ItemReader itemReader,
            CountItemWriter itemWriter) {

        return new StepBuilder("weeklyActiveUserStep", jobRepository)
                .<String, String>chunk(10, transactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }

    @Bean("weeklyActiveUserFileWriteStep")
    @JobScope
    public Step fileWrite(JobRepository repository,
            PlatformTransactionManager transactionManager,
            WeeklyActiveUserFileTasklet tasklet) {

        return new StepBuilder("weeklyActiveUserFileWriteStep", repository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

}
