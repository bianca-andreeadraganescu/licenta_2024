package com.example.demo.userip;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.demo.userip",
        entityManagerFactoryRef = "userIpEntityManagerFactory",
        transactionManagerRef = "userIpTransactionManager"
)
public class UserIpDataSourceConfig {

    @Value("${spring.datasource.user-ip-db.url}")
    private String userIpDbUrl;

    @Value("${spring.datasource.user-ip-db.username}")
    private String userIpDbUsername;

    @Value("${spring.datasource.user-ip-db.password}")
    private String userIpDbPassword;

    @Value("${spring.datasource.user-ip-db.driver-class-name}")
    private String userIpDbDriverClassName;

    @Bean(name = "userIpDataSource")
    public DataSource userIpDataSource() {
        return DataSourceBuilder.create()
                .url(userIpDbUrl)
                .username(userIpDbUsername)
                .password(userIpDbPassword)
                .driverClassName(userIpDbDriverClassName)
                .build();
    }

    @Bean(name = "userIpEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean userIpEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("userIpDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.demo.userip")
                .persistenceUnit("userIp")
                .build();
    }

    @Bean(name = "userIpTransactionManager")
    public PlatformTransactionManager userIpTransactionManager(
            @Qualifier("userIpEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
