package com.davita.pie.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by taroy on 4/25/16.
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = "com.davita.pie.domain")
@EnableJpaRepositories(basePackages = "com.davita.pie.repository")
@EnableTransactionManagement
public class RepositoryConfiguration {

//    @Autowired
//    Environment environment;
//
//    @Bean(name = "datasource")
//    public ComboPooledDataSource dataSource() throws PropertyVetoException {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass(environment.getRequiredProperty("c3p0.driver"));
//        dataSource.setJdbcUrl(environment.getRequiredProperty("c3p0.url"));
//        dataSource.setUser(environment.getRequiredProperty("c3p0.user"));
//        dataSource.setPassword(environment.getRequiredProperty("c3p0.password"));
//        dataSource.setInitialPoolSize(environment.getRequiredProperty("c3p0.initialPoolSize", Integer.class));
//        dataSource.setMaxPoolSize(environment.getRequiredProperty("c3p0.maxPoolSize", Integer.class));
//        dataSource.setMinPoolSize(environment.getRequiredProperty("c3p0.minPoolSize", Integer.class));
//        dataSource.setAcquireIncrement(environment.getRequiredProperty("c3p0.acquireIncrement", Integer.class));
//        dataSource.setMaxStatements(environment.getRequiredProperty("c3p0.maxStatements", Integer.class));
//        dataSource.setMaxIdleTime(environment.getRequiredProperty("c3p0.maxIdleTime", Integer.class));
//        return dataSource;
//    }
}
