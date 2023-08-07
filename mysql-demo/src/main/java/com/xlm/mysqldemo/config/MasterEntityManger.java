package com.xlm.mysqldemo.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.Map;
import java.util.Objects;

/**
 * @author xlm
 * @date 2023/7/25 下午3:53
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.xlm.mysqldemo.dao.master", entityManagerFactoryRef = "primaryEntityManagerFactory")
public class MasterEntityManger {

    @Resource
    AtomikosDataSourceBean masterDataSource;

    @Resource
    private HibernateProperties hibernateProperties;

    @Resource
    private JpaProperties jpaProperties;


    private Map<String, Object> getHibernateProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory (EntityManagerFactoryBuilder builder) {
        return builder.dataSource(masterDataSource)
                .properties(getHibernateProperties())
                //扫描entity注解 不是repository
                .packages("com.xlm.mysqldemo.pojo.master")
                .persistenceUnit("primaryPersistenceUnit")
                .jta(true)
                .build();
    }

    @Primary
    @Bean(name = "entityManagerPrimary")
    public EntityManager entityManagerPrimary(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(primaryEntityManagerFactory(builder).getObject()).createEntityManager();
    }
}
