package com.simplevoting.menuvoting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
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
@EnableCaching
@EnableJpaRepositories(basePackages = "com.simplevoting.menuvoting.repository")
public class JpaConfig {

    private Environment env;

    @Autowired
    public JpaConfig(Environment env) {
        this.env = env;
    }

    @Bean
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
        jpaProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        jpaProperties.put("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
        jpaProperties.put("hibernate.cache.use_query_cache", "true");
        jpaProperties.put("hibernate.cache.use_second_level_cache", "true");



        localContainerEntityManagerFactory.setJpaProperties(jpaProperties);

        return localContainerEntityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);

        return cacheManagerFactoryBean;
    }
}
