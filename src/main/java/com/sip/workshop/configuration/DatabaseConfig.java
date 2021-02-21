package com.sip.workshop.configuration;
import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;
@Configuration
public class DatabaseConfig {
    @Value("${postgres://zqoqoflaycoqqk:92618819428f02451929590f82d004be3f102d78ed1d4513a9b4ca14f4fc8872@ec2-54-216-185-51.eu-west-1.compute.amazonaws.com:5432/devl8m4vh9gdbf}")
    private String dbUrl;
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        return new HikariDataSource(config);
    }
}
