package com.thisisaniceteam.batch.scheduler;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BatchScheduler {

    private final JobLauncher jobLauncher;

    @Qualifier("dailyRegisteredUserJob")
    private final Job dailyRegisteredUserJob;

    @Qualifier("dailyTransmissionJob")
    private final Job dailyTransmissionJob;

    @Qualifier("weeklyActiveUserJob")
    private final Job weeklyActiveUserJob;

    // 매일 4시 실행
    @Scheduled(cron = "0 0 4 * * *")
    public void runDaily() throws Exception {
        // Job Parameter 설정
        Map<String, JobParameter<?>> parameter = new HashMap<>();
        setDateParam(parameter);
        JobParameters parameters = new JobParameters(parameter);

        // Job 시행
        jobLauncher.run(dailyRegisteredUserJob, parameters);
        jobLauncher.run(dailyTransmissionJob, parameters);
    }

    // 매주 일요일 5시 실행
    @Scheduled(cron = "0 0 5 * * 0")
    public void runWeekly() throws Exception {
        // Job Parameter 설정
        Map<String, JobParameter<?>> parameter = new HashMap<>();
        setDateParam(parameter);
        JobParameters parameters = new JobParameters(parameter);

        // Job 시행
        jobLauncher.run(weeklyActiveUserJob, parameters);
    }

    private void setDateParam(Map<String, JobParameter<?>> parameter) {
        String dateString = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        parameter.put("date", new JobParameter(dateString, String.class));
    }
}
