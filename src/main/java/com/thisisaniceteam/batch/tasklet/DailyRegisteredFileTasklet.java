package com.thisisaniceteam.batch.tasklet;

import java.util.Map;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class DailyRegisteredFileTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Map<String, Object> context = chunkContext.getStepContext().getJobExecutionContext();

        System.out.println(context.keySet());

        if (context.containsKey("count")) {
            // File Write
            System.out.println((int) context.get("count"));
        }

        else {
            // Zero Value Write
            System.out.println("failed");
        }

        return RepeatStatus.FINISHED;
    }

}
