package com.wesjordan.billingcontract.mapping;

import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.dto.ProductADto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductAMapper extends ConfigurableMapper{
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(ProductA.class, ProductADto.class)
                .field("accountId","accountId")
                .field("charge", "charge")
                .field("setupCharge", "setupCharge")
                .field("startDate", "startDate")
                .field("contractLength", "contractLength")
                .field("billingFrequency", "billingFrequency")
                .byDefault()
                .register();

        factory.getConverterFactory().registerConverter(new MoneyConverter());
    }
}
