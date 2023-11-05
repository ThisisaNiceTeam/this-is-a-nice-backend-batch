package com.thisisaniceteam.batch.item.writer;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class CountItemWriter<T> implements ItemWriter<T> {

    private StepExecution stepExecution;

    @Override
    public void write(Chunk<? extends T> items) throws Exception {
        ExecutionContext context = this.stepExecution.getExecutionContext();

        int count = context.getInt("count");
        context.put("count", count + items.size());
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        stepExecution.getExecutionContext().put("count", 0);
    }

    @AfterStep
    public void saveJobExecution(StepExecution stepExecution) {
        int count = stepExecution.getExecutionContext().getInt("count");
        stepExecution.getJobExecution().getExecutionContext().put("count", count);
    }

}
