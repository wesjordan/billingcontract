package com.wesjordan.billingcontract.stream.consumer;

import com.wesjordan.billingcontract.domain.Money;
import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.mapping.ProductAMapper;
import com.wesjordan.billingcontract.service.ProductAService;
import com.wesjordan.billingcontract.stream.event.ProductAEventMessage;
import com.wesjordan.billingcontract.stream.event.ProductAEventType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
//(classes = {ProductAConsumer.class, ProductAConsumerConfig.class, ProductAService.class, ProductAMapper.class, KafkaListenerEndpointRegistry.class})
@DirtiesContext
public class ProductAConsumerTest {

    private static String CONSUMING_TOPIC = "ProductATest";

    // @InjectMocks
    @Autowired
    ProductAConsumer productAConsumer;

    @MockBean
    ProductAService productAService;

    @Autowired
    private ProductAMapper mapper;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private KafkaTemplate<String, ProductAEventMessage> template;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, CONSUMING_TOPIC);

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        //producer properties
        Map<String, Object> producerProps = KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        //producer factory
        ProducerFactory<String, ProductAEventMessage> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);

        template = new KafkaTemplate<>(producerFactory);

        template.setDefaultTopic(CONSUMING_TOPIC);

        //wait for partitions to be assigned
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafka.getPartitionsPerTopic());
        }
    }

    @Test
    public void testConsumerMessage() throws InterruptedException {
        //send message
        ProductADto prod = new ProductADto();
        prod.setAccountId(1L);
        prod.setCharge(Money.usd(BigDecimal.valueOf(1200L)));
        prod.setSetupCharge(Money.usd(BigDecimal.valueOf(300L)));

        ProductAEventMessage message = new ProductAEventMessage(ProductAEventType.PRODUCT_A_CREATED, prod);

        template.sendDefault(message);

        log.debug("template sent message: {}", message);


        productAConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);

        ProductA productA = mapper.map(prod, ProductA.class);
        verify(productAService, times(1)).saveProductA(any(ProductA.class));
    }
}
