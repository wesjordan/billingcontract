package com.wesjordan.billingcontract.stream.producer;

import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.stream.event.ProductAEventMessage;
import com.wesjordan.billingcontract.stream.event.ProductAEventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class ProductAProducer {

    private static final Log logger = LogFactory.getLog(ProductAProducer.class);
    private KafkaTemplate<String, ProductAEventMessage> kafkaTemplate;


    @Autowired
    public ProductAProducer(KafkaTemplate<String, ProductAEventMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void publishProductACreatedEvent(ProductADto productADto){
        ProductAEventMessage create = new ProductAEventMessage();
        create.setEvent(ProductAEventType.PRODUCT_A_CREATED);
        create.setPayload(productADto);
        publish(create);
    }

    public void publishProductAUpdatedEvent(ProductADto productADto){
        ProductAEventMessage update = new ProductAEventMessage();
        update.setEvent(ProductAEventType.PRODUCT_A_UPDATED);
        update.setPayload(productADto);
        publish(update);
    }

    private void publish(ProductAEventMessage data) {
        ProducerRecord<String, ProductAEventMessage> record = new ProducerRecord<>("ProductA", data);

        ListenableFuture<SendResult<String, ProductAEventMessage>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ProductAEventMessage>>() {
            @Override
            public void onFailure(Throwable throwable) {
                    logger.error(throwable.getMessage());
                }
                @Override
                public void onSuccess(SendResult<String, ProductAEventMessage> productASendResult) {
                    logger.info("event message written to kafka!");
                }
        });

    }
}
