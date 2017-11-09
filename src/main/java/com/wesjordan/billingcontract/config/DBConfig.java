package com.wesjordan.billingcontract.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DBConfig {

    @Bean
    public MysqlDataSource dataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("BILLING_CONTRACT_DB"));

        String username = dbUri.getUserInfo().split(":")[0];
        String pwd = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:mysql://".concat(dbUri.getHost()).concat(dbUri.getPath()).concat("?createDatabaseIfNotExist=true");

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(pwd);
        dataSource.setURL(dbUrl);

        return dataSource;
    }
}
