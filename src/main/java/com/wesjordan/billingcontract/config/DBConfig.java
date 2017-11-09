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

        MysqlDataSource dataSource = new MysqlDataSource();

        String username = dbUri.getUserInfo().split(":")[0];
        dataSource.setUser(username);

        if (dbUri.getUserInfo().split(":").length == 2) {        //db's with no pwd
            String pwd = dbUri.getUserInfo().split(":")[1];
            dataSource.setPassword(pwd);
        }

        String dbUrl = "jdbc:mysql://".concat(dbUri.getHost()).concat(dbUri.getPath()).concat("?createDatabaseIfNotExist=true");
        dataSource.setURL(dbUrl);

        return dataSource;
    }
}
