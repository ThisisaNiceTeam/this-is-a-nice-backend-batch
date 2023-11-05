package com.thisisaniceteam.batch.job;

import com.thisisaniceteam.batch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class DailyRegisteredUserConfig {

    @Bean
    public Job getDailyRegisteredUserJob(JobRepository repository,
            @Qualifier("dailyRegisteredUserStep") Step step) {
        return new JobBuilder("dailyRegisteredUserStep", repository)
                .start(step)
                .build();
    }

    @Bean("dailyRegisteredUserStep")
    @JobScope
    public Step getDailyRegisteredUserStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader itemReader) {
        return new StepBuilder("dailyRegisteredUserStep", jobRepository)
                .<String, String> chunk(10, transactionManager)
                .reader(itemReader)
                .processor(processor())
                .writer(itemWriter())
                .build();
    }

    private ItemProcessor<User, User> processor() {
        return user -> {
            System.out.println(user.getAccount());

            return user;
        };
    }

    private ItemWriter<User> itemWriter() {
        return list -> {
            System.out.println();
            for (User user : list) {
                System.out.println(user);
            }
            System.out.println();
        };
    }

}
