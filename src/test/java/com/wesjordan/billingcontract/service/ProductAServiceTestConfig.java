package com.wesjordan.billingcontract.service;

import com.wesjordan.billingcontract.mapping.ProductAMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductAServiceTestConfig {

    @Bean
    public ProductAMapper productAMapper() {
        return new ProductAMapper();
    }


}
