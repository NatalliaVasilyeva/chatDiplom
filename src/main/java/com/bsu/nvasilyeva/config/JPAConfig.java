package com.bsu.nvasilyeva.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Create bean and set different properties related to database an hibernate
 */

@Configuration
@EnableTransactionManagement
public class JPAConfig {

    /**
     * Get factory to use the CRUD and other operations with Spring JPA
     *
     * @return factory
     */

    @Bean
    public LocalContainerEntityManagerFactoryBean getFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(getDataSource());
        factory.setPackagesToScan("com.bsu.nvasilyeva.entity");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(getProperties());
        return factory;
    }

    /**
     * Get properties of hibernate to use this features with database
     *
     * @return properties
     */


    public Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL55Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.jdbc.batch_size", "15");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        return properties;
    }

    /**
     * Set driver and path to database for this project for get pool connections
     *
     * @return dataSource
     */

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://localhost:3306/krishi_chat?useUnicode=true&serverTimezone=UTC&useSSL=false");
       return dataSource;
    }


    /**
     * Create manager to help make transaction in database using Spring Framework
     * @return jpa transaction manager
     */
    @Bean
    public PlatformTransactionManager getTransactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(getFactory().getObject());
        return manager;
    }
}
