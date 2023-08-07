package com.xlm.mysqldemo.config;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.jta.JtaTransactionManager;

import java.util.Properties;

/**
 * @author xlm
 * @date 2023/7/4 上午9:48
 */
@Configuration
public class AtomikosDataSourceConfig {

    @Value("${spring.datasource.master.jdbc-url}")
    private String masterDataSourceUrl;

    @Value("${spring.datasource.slave.jdbc-url}")
    private String slaveDataSourceUrl;

    @Value("${spring.datasource.master.username}")
    private String dataSourceUserName;

    @Value("${spring.datasource.master.password}")
    private String dataSourcePassword;

    @Value("${spring.jta.atomikos.datasource.xa-data-source-class-name}")
    private String XA_DS_CLASS_NAME;


    /**
     * 第一个数据库
     *
     */
    @Bean(name = "masterDataSource")
    @Primary
    @Qualifier("masterDataSource")
    public AtomikosDataSourceBean masterDataSource() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName("masterDataSource");
        atomikosDataSourceBean.setXaDataSourceClassName(XA_DS_CLASS_NAME);
        Properties properties = new Properties();
        properties.put("url", masterDataSourceUrl);
        properties.put("user", dataSourceUserName);
        properties.put("password", dataSourcePassword);
        atomikosDataSourceBean.setXaProperties(properties);
        return atomikosDataSourceBean;
    }

    /**
     * 第二个数据库
     *
     */
    @Bean(name = "slaveDataSource")
    @Qualifier("slaveDataSource")
    public AtomikosDataSourceBean slaveDataSource() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setUniqueResourceName("slaveDataSource");
        atomikosDataSourceBean.setXaDataSourceClassName(XA_DS_CLASS_NAME);
        Properties properties = new Properties();
        properties.put("url", slaveDataSourceUrl);
        properties.put("user", dataSourceUserName);
        properties.put("password", dataSourcePassword);
        atomikosDataSourceBean.setXaProperties(properties);
        return atomikosDataSourceBean;
    }

    @Bean(destroyMethod = "close", initMethod = "init")
    public UserTransactionManager userTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }

    /**
     * 定义XA事务管理器（TM）
     */
    @Bean(name = "transactionManager")
    public JtaTransactionManager transactionManager() {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        jtaTransactionManager.setTransactionManager(userTransactionManager());
        return jtaTransactionManager;
    }

}

