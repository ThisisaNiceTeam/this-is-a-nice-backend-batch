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

@Configuration
@RequiredArgsConstructor
public class WeeklyActivateUserItemReader {

    private final DataSource dataSource;

    @Bean("weeklyActivateUserReader")
    @StepScope
    public JdbcPagingItemReader<User> weeklyActivateUserReader(
            @Value("#{jobParameters[date]}") String date
    ) throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("date", date);

        return new JdbcPagingItemReaderBuilder<User>()
                .name("weeklyActivateUserReader")
                .dataSource(dataSource)
                .queryProvider(createWeeklyActivateUserQueryProvider())
                .parameterValues(params)
                .rowMapper(new BeanPropertyRowMapper<>(User.class))
                .build();
    }

    @Bean
    public PagingQueryProvider createWeeklyActivateUserQueryProvider() throws Exception {

        SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();

        factoryBean.setSelectClause("SELECT id");
        factoryBean.setFromClause("FROM user AS u");
        factoryBean.setWhereClause("WHERE EXISTS ( "
                + "SELECT 1 "
                + "FROM chat AS c "
                + "WHERE u.id = c.sender "
                + "AND DATE(c.created_at) BETWEEN :date "
                + "AND (:date + INTERVAL 1 DAY - INTERVAL 1 SECOND) "
                + ") ");
        factoryBean.setSortKey("id");
        factoryBean.setDataSource(dataSource);

        return factoryBean.getObject();
    }

}
