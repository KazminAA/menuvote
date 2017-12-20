package com.simplevoting.menuvoting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"com.simplevoting.menuvoting"})
@PropertySource("classpath:/application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.simplevoting.menuvoting.repository")
public class JpaConfig {

    private Environment env;

    @Autowired
    public JpaConfig(Environment env) {
        this.env = env;
    }

    @Bean(destroyMethod = "close")
    public javax.sql.DataSource datasource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getRequiredProperty("spring.datasource.url"));
        dataSource.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("spring.datasource.password"));
        dataSource.setInitialSize(Integer.parseInt(env.getRequiredProperty("spring.datasource.tomcat.initial-size")));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        localContainerEntityManagerFactory.setPackagesToScan("com.simplevoting.menuvoting.model");
        localContainerEntityManagerFactory.setDataSource(dataSource);
        localContainerEntityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", env.getProperty("spring.jpa.database-platform"));
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show.sql", env.getProperty("spring.jpa.properties.hibernate.show_sql"));
        jpaProperties.put("hibernate.format.sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));

        localContainerEntityManagerFactory.setJpaProperties(jpaProperties);

        return localContainerEntityManagerFactory;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
