package com.thisisaniceteam.batch.tasklet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@StepScope
@Component
public class DailyRegisteredFileTasklet implements Tasklet {

    @Value("#{jobParameters[date]}")
    private String date;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Map<String, Object> context = chunkContext.getStepContext().getJobExecutionContext();

        File file = new File("C:/projects/this-is-a-nice-backend-batch/src/main/resources/static/daily_registered_count.csv");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));

        bw.write(date + "," + context.get("count"));
        bw.newLine();
        bw.flush();
        bw.close();

        return RepeatStatus.FINISHED;
    }

}
