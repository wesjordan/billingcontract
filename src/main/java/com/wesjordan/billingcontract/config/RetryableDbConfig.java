package com.wesjordan.billingcontract.config;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.net.URISyntaxException;
import java.sql.SQLException;

public interface RetryableDbConfig {

    @Retryable(value = {Exception.class}, maxAttempts = 10, backoff = @Backoff(multiplier = 2.3, maxDelay = 30000))
    MysqlDataSource dataSource() throws URISyntaxException, SQLException;
}
