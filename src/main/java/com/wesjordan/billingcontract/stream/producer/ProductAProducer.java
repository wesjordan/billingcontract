package com.wesjordan.billingcontract.stream.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.stream.event.ProductACreatedEvent;
import com.wesjordan.billingcontract.stream.event.ProductAEvent;
import com.wesjordan.billingcontract.stream.event.ProductAUpdatedEvent;
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
    private KafkaTemplate<Integer,String> kafkaTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public ProductAProducer(KafkaTemplate<Integer,String> kafkaTemplate, ObjectMapper objectMapper){
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishProductACreatedEvent(ProductADto productADto){
        String data = serializeToString(new ProductACreatedEvent(productADto));

        publish(data);
    }

    public void publishProductAUpdatedEvent(ProductADto productADto){
        String data = serializeToString(new ProductAUpdatedEvent(productADto));

        publish(data);
    }

    private String serializeToString(ProductAEvent productAEvent){
        String data = "";

        try {
            data = objectMapper.writeValueAsString(productAEvent);
        } catch (JsonProcessingException e) {
            logger.error("unable to serialize object for publishing to kafka", e);
        }

        return data;
    }

    private void publish(String data){
        ProducerRecord<Integer, String> record = new ProducerRecord<>("ProductA", data);

        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                    logger.error(throwable.getMessage());
                }
                @Override
                public void onSuccess(SendResult<Integer, String> integerStringSendResult) {
                    logger.info("event message written to kafka!");
                }
        });

    }
}
