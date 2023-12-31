package com.thisisaniceteam.batch.job;

import com.thisisaniceteam.batch.item.processor.DailyTransmissionItemProcessor;
import com.thisisaniceteam.batch.item.writer.CountItemWriter;
import com.thisisaniceteam.batch.tasklet.DailyTransmissionByTimeTasklet;
import com.thisisaniceteam.batch.tasklet.DailyTransmissionFileTasklet;
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
public class DailyTransmissionConfig {

    @Bean("dailyTransmissionJob")
    public Job getDailyTransmissionJob(JobRepository repository,
            @Qualifier("dailyTransmissionStep") Step aggregateStep,
            @Qualifier("dailyTransmissionFileWriteStep") Step fileWriteStep,
            @Qualifier("countByTimeFileWriteStep") Step countByTimeFileWriteStep) {
        return new JobBuilder("dailyTransmissionJob", repository)
                .start(aggregateStep)
                .next(fileWriteStep)
                .next(countByTimeFileWriteStep)
                .build();
    }

    @Bean("dailyTransmissionStep")
    @JobScope
    public Step getDailyTransmissionStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            @Qualifier("dailyTransmissionReader") ItemReader itemReader,
            DailyTransmissionItemProcessor itemProcessor,
            CountItemWriter itemWriter) {

        return new StepBuilder("dailyTransmissionStep", jobRepository)
                .<String, String>chunk(10, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean("dailyTransmissionFileWriteStep")
    @JobScope
    public Step fileWrite(JobRepository repository,
            PlatformTransactionManager transactionManager,
            DailyTransmissionFileTasklet tasklet) {

        return new StepBuilder("dailyTransmissionFileWriteStep", repository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

    @Bean("countByTimeFileWriteStep")
    @JobScope
    public Step countByTimeFileWriteStep(JobRepository repository,
            PlatformTransactionManager transactionManager,
            DailyTransmissionByTimeTasklet tasklet) {

        return new StepBuilder("countByTimeFileWriteStep", repository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

}
