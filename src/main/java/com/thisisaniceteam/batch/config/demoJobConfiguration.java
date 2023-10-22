package com.thisisaniceteam.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class demoJobConfiguration {

    @Bean
    public Job demoJob(JobRepository repository, Step step) {
        return new JobBuilder("demoJob", repository)
                .start(step)
                .build();
    }

    @Bean
    @JobScope
    public Step demoStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            Tasklet demoTask) {
        return new StepBuilder("myStep", jobRepository)
                .tasklet(demoTask, transactionManager) // or .chunk(chunkSize, transactionManager)
                .build();
    }
}
