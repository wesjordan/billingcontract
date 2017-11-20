package com.wesjordan.billingcontract.stream.config;

import com.wesjordan.billingcontract.stream.event.ProductAEventMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProductAProducerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaTemplate<String, ProductAEventMessage> producerKafkaTemplate() {
        return new KafkaTemplate<>(getProductAProducerFactory());
    }

    @Bean
    public ProducerFactory<String, ProductAEventMessage> getProductAProducerFactory() {
        return new DefaultKafkaProducerFactory<>(productAProducerConfigs());
    }

    @Bean
    public Map<String, Object> productAProducerConfigs(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);//TODO: get from config server
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }
}
