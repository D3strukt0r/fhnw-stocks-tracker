/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class TokenSecurityConfiguration {
    /**
     * The entity manager factory.
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * The entity manager factory for security.
     */
    private EntityManagerFactory entityManagerFactorySecurity;

    /**
     * Setup the security.
     *
     * @param dataSource           The data source.
     * @param vendorAdapter        The vendor adapter
     * @param entityManagerFactory The entity manager factory.
     */
    @Autowired
    public TokenSecurityConfiguration(
        final DataSource dataSource,
        final JpaVendorAdapter vendorAdapter,
        final EntityManagerFactory entityManagerFactory
    ) {
        this.entityManagerFactory = entityManagerFactory;
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("security.model");
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(additionalProperties());
        entityManagerFactoryBean.afterPropertiesSet();
        this.entityManagerFactorySecurity = entityManagerFactoryBean.getObject();
    }

    /**
     * Create the entity manager.
     *
     * @return Returns the entity manager.
     */
    @Bean
    public EntityManager entityManagerSecurity() {
        return entityManagerFactorySecurity.createEntityManager();
    }

    /**
     * Additional properties for the entity manager factory.
     *
     * @return Returns the properties.
     */
    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.putAll(entityManagerFactory.getProperties());
        properties.remove("hibernate.transaction.coordinator_class"); //Spring Data issue
        return properties;
    }
}
