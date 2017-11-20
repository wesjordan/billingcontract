package com.wesjordan.billingcontract.stream.consumer;

import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.mapping.ProductAMapper;
import com.wesjordan.billingcontract.service.ProductAService;
import com.wesjordan.billingcontract.stream.event.ProductAEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductAConsumer {

    private ProductAService productAService;
    private ProductAMapper productAMapper;

    @Autowired
    public ProductAConsumer(ProductAService productAService, ProductAMapper productAMapper) {
        this.productAService = productAService;
        this.productAMapper = productAMapper;
    }

    @KafkaListener(topics = "ProductA", groupId = "productAConsumerGroup")
    public void readProductAEvent(ProductAEventMessage message) {

        if (message != null) {
            log.debug("Message Received: ".concat(message.toString()));
            ProductA productA = productAMapper.map(message.getPayload(), ProductA.class);
            productAService.saveProductA(productA);
        } else {
            log.error("null message received from stream");
        }
    }
}
