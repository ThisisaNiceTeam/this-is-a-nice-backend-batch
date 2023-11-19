package com.thisisaniceteam.batch.item.reader;

import com.thisisaniceteam.batch.model.Chat;
import com.thisisaniceteam.batch.model.User;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@RequiredArgsConstructor
@Configuration
public class DailyTransmissionItemReader {

    private final DataSource dataSource;

    @Bean("dailyTransmissionReader")
    @StepScope
    public JdbcPagingItemReader<Chat> jdbcPagingItemReader(
            @Value("#{jobParameters[date]}") String date
    ) throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("date", date);

        return new JdbcPagingItemReaderBuilder<Chat>()
                .name("dailyTransmissionReader")
                .dataSource(dataSource)
                .queryProvider(createDailyTransmissionQueryProvider())
                .parameterValues(params)
                .rowMapper(new BeanPropertyRowMapper<>(Chat.class))
                .build();
    }

    @Bean
    public PagingQueryProvider createDailyTransmissionQueryProvider() throws Exception {

        SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();

        factoryBean.setSelectClause("SELECT *");
        factoryBean.setFromClause("FROM chat");
        factoryBean.setWhereClause("WHERE DATE(created_at) = :date");
        factoryBean.setSortKey("id");
        factoryBean.setDataSource(dataSource);

        return factoryBean.getObject();
    }

}
