package com.wesjordan.billingcontract.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Configuration
@EnableRetry
public class DBConfig {

    private static final Log logger = LogFactory.getLog(DBConfig.class);

    @Bean
    @Retryable(value = {Exception.class}, maxAttempts = 10, backoff = @Backoff(multiplier = 2.3, maxDelay = 30000))
    public MysqlDataSource dataSource() throws URISyntaxException, SQLException {
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

        logger.debug("Trying/Re-trying to attain db connection");
        dataSource.getConnection();             //if this method throws an exception (DB potentially unreachable) it'll retry based on config in interface

        return dataSource;
    }

}
