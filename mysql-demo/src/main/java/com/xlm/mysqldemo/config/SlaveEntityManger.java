package com.xlm.mysqldemo.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(basePackages = "com.xlm.mysqldemo.dao.slave", entityManagerFactoryRef = "slaveEntityManagerFactory")
public class SlaveEntityManger {

    @Resource
    AtomikosDataSourceBean slaveDataSource;

    @Resource
    private HibernateProperties hibernateProperties;

    @Resource
    private JpaProperties jpaProperties;


    private Map<String, Object> getHibernateProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }


    @Bean(name = "slaveEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean slaveEntityManagerFactory (EntityManagerFactoryBuilder builder) {
        return builder.dataSource(slaveDataSource)
                .properties(getHibernateProperties())
                .packages("com.xlm.mysqldemo.pojo.slave")
                .persistenceUnit("slavePersistenceUnit")
                .jta(true)
                .build();
    }

    @Bean(name = "entityManagerSlave")
    public EntityManager entityManagerSlave(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(slaveEntityManagerFactory(builder).getObject()).createEntityManager();
    }
}
