package com.thisisaniceteam.batch.item.reader;

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
public class DailyRegisteredUserItemReader {

    private final DataSource dataSource;

    @Bean
    @StepScope
    public JdbcPagingItemReader<User> jdbcPagingItemReader(
            @Value("#{jobParameters[date]}") String date
    ) throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("date", date);

        return new JdbcPagingItemReaderBuilder<User>()
                .name("dailyRegisteredUserReader")
                .dataSource(dataSource)
                .queryProvider(createQueryProvider())
                .parameterValues(params)
                .rowMapper(new BeanPropertyRowMapper<>(User.class))
                .build();
    }

    @Bean
    public PagingQueryProvider createQueryProvider() throws Exception {

        SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();

        factoryBean.setSelectClause("SELECT *");
        factoryBean.setFromClause("FROM user");
        factoryBean.setWhereClause("WHERE DATE(created_at) = :date");
        factoryBean.setSortKey("id");
        factoryBean.setDataSource(dataSource);

        return factoryBean.getObject();
    }
}
