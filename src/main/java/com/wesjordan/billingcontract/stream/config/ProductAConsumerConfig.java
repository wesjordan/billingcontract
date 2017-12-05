package com.wesjordan.billingcontract.stream.config;

import com.wesjordan.billingcontract.stream.consumer.ProductAConsumer;
import com.wesjordan.billingcontract.stream.event.ProductAEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableKafka
@Configuration
public class ProductAConsumerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${productA.topic.consume}")
    private String springIntegrationKafkaTopic;

    private ProductAConsumer productAConsumer;

    @Autowired
    public ProductAConsumerConfig(ProductAConsumer productAConsumer) {
        this.productAConsumer = productAConsumer;
    }

    @Bean
    public DirectChannel consumingChannel() {
        return new DirectChannel();
    }

    @Bean
    public KafkaMessageDrivenChannelAdapter<String, ProductAEventMessage> kafkaMessageDrivenChannelAdapter() {
        KafkaMessageDrivenChannelAdapter<String, ProductAEventMessage> kafkaMessageDrivenChannelAdapter = new KafkaMessageDrivenChannelAdapter<>(messageListenerContainer());
        kafkaMessageDrivenChannelAdapter.setOutputChannel(consumingChannel());

        return kafkaMessageDrivenChannelAdapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "consumingChannel")
    public ProductAConsumer countDownLatchHandler() {
        return productAConsumer;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, ProductAEventMessage> messageListenerContainer() {
        ContainerProperties containerProperties = new ContainerProperties(springIntegrationKafkaTopic);

        return new ConcurrentMessageListenerContainer<>(productAListenerConsumerFactory(), containerProperties);
    }

    @Bean
    public ConsumerFactory<String, ProductAEventMessage> productAListenerConsumerFactory() {
        Map<String, Object> containerProperties = new HashMap<>();
        containerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        containerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "productAConsumerGroup");

        return new DefaultKafkaConsumerFactory<>(containerProperties, new StringDeserializer(), new JsonDeserializer<>(ProductAEventMessage.class));
    }
}
