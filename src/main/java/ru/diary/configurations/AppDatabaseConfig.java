package ru.diary.configurations;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
@PropertySource(value = "classpath:db_jdbc.properties", encoding = "UTF-8")
@PropertySource(value = "classpath:sql.properties",encoding = "UTF-8")
@ComponentScan(basePackages = {"ru.diary.services", "ru.diary.repositories"})
@EnableTransactionManagement
public class AppDatabaseConfig {

    Environment environment;

    @Autowired
    public AppDatabaseConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        var username = Objects.requireNonNull(environment.getProperty("jdbc.username"));
        var password = Objects.requireNonNull(environment.getProperty("jdbc.password"));
        var url = Objects.requireNonNull(environment.getProperty("jdbc.url"));
        var jdbcDriver = Objects.requireNonNull(environment.getProperty("jdbc.jdbcDriver"));

        var dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName(jdbcDriver);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }


    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        return new JdbcTransactionManager(dataSource());
    }


}
