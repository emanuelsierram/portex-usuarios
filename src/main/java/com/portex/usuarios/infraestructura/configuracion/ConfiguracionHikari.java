package com.portex.usuarios.infraestructura.configuracion;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Profile("desarrollo")
public class ConfiguracionHikari extends HikariConfig {

    @Value("${spring.datasource.hikari.poolName}")
    private String poolName;

    @Bean
    public DataSource dataSource() {
        setMaximumPoolSize(Runtime.getRuntime().availableProcessors() * 2 + 1);
        setPoolName(poolName);
        return new HikariDataSource(this);
    }
}
