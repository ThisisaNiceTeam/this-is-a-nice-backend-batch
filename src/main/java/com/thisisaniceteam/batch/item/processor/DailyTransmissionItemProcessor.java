package com.thisisaniceteam.batch.item.processor;

import com.thisisaniceteam.batch.model.Chat;
import java.util.Calendar;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("DailyTransmissionItemProcessor")
@StepScope
public class DailyTransmissionItemProcessor implements ItemProcessor {

    private StepExecution stepExecution;

    @Override
    public Object process(Object item) throws Exception {

        Chat curr = (Chat) item;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curr.getCreatedAt());

        ExecutionContext context = this.stepExecution.getExecutionContext();
        int[] count = (int[]) context.get("countByTime");

        if (count == null) throw new Exception();
        ++count[calendar.get(Calendar.HOUR_OF_DAY)];

        return item;
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;

        int[] transmission = new int[24];
        stepExecution.getExecutionContext().put("countByTime", transmission);
    }

    @AfterStep
    public void saveJobExecution(StepExecution stepExecution) throws Exception {
        int[] count = (int[]) stepExecution.getExecutionContext().get("countByTime");

        if (count == null) throw new Exception();
        stepExecution.getJobExecution().getExecutionContext().put("countByTime", count);
    }
}
