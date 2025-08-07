package com.runasimi_edu.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    // DataSource para desarrollo local (MariaDB)
    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setUrl("jdbc:mariadb://localhost:3306/is2");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");
        return dataSource;
    }

    // DataSource para producción (Railway MySQL)
    @Bean
    @Profile("prod")
    public DataSource prodDataSource(@Value("${DATABASE_URL}") String databaseUrl) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        
        // Railway proporciona la URL completa, solo necesitamos ajustar el protocolo
        String mysqlUrl = databaseUrl.replace("mysql://", "jdbc:mysql://");
        dataSource.setUrl(mysqlUrl + "?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        
        return dataSource;
    }

    // DataSource para testing (H2 en memoria)
    @Bean
    @Profile("test")
    public DataSource testDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}