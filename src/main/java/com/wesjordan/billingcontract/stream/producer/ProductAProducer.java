package com.wesjordan.billingcontract.stream.producer;

import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.stream.event.ProductAEventMessage;
import com.wesjordan.billingcontract.stream.event.ProductAEventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductAProducer {
    private DirectChannel producingChannel;

    @Autowired
    public ProductAProducer(@Lazy DirectChannel producingChannel) {
        this.producingChannel = producingChannel;
    }

    public void publishProductACreatedEvent(ProductADto productADto){
        ProductAEventMessage create = new ProductAEventMessage(ProductAEventType.PRODUCT_A_CREATED, productADto);
        publish(create);
    }

    public void publishProductAUpdatedEvent(ProductADto productADto){
        ProductAEventMessage update = new ProductAEventMessage(ProductAEventType.PRODUCT_A_UPDATED, productADto);
        publish(update);
    }

    private void publish(ProductAEventMessage data) {
        Message m = MessageBuilder.withPayload(data).build();
        producingChannel.send(m);
        log.info("event message published to kafka!");
    }
}
