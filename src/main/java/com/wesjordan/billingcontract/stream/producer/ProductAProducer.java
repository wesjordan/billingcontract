package com.wesjordan.billingcontract.stream.producer;

import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.stream.event.ProductAEventMessage;
import com.wesjordan.billingcontract.stream.event.ProductAEventType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class ProductAProducer {

    @Value("${productA.topic.publish}")
    private String productAPublishTopic;

    private KafkaTemplate<String, ProductAEventMessage> kafkaTemplate;

    @Autowired
    public ProductAProducer(KafkaTemplate<String, ProductAEventMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
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
        ProducerRecord<String, ProductAEventMessage> record = new ProducerRecord<>(productAPublishTopic, data);

        ListenableFuture<SendResult<String, ProductAEventMessage>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ProductAEventMessage>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error(throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ProductAEventMessage> productASendResult) {
                log.info("event message written to kafka!");
            }
        });

    }
}
