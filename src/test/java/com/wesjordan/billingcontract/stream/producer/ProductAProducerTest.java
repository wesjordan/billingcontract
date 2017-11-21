package com.wesjordan.billingcontract.stream.producer;

import com.wesjordan.billingcontract.domain.Money;
import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.stream.event.ProductAEventMessage;
import com.wesjordan.billingcontract.stream.event.ProductAEventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class ProductAProducerTest {
    private static final Logger log = LoggerFactory.getLogger(ProductAProducerTest.class);

    private static String PUBLISHING_TOPIC = "ProductA";

    @Autowired
    ProductAProducer productAProducer;

    private KafkaMessageListenerContainer<String, ProductAEventMessage> container;
    private BlockingQueue<ConsumerRecord<String, ProductAEventMessage>> records;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, PUBLISHING_TOPIC);


    @Before
    public void setUp() throws Exception {
        //init consumer props
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("publisher", "false", embeddedKafka);

        //consumer factory
        DefaultKafkaConsumerFactory<String, ProductAEventMessage> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps, null, new JsonDeserializer<>(ProductAEventMessage.class));

        //topic
        ContainerProperties containerProps = new ContainerProperties(PUBLISHING_TOPIC);

        //kafka message listener container
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProps);

        //message store
        records = new LinkedBlockingDeque<>();

        //kafka listener
        container.setupMessageListener((MessageListener<String, ProductAEventMessage>) record -> {
            log.debug("test listener received message: " + record.toString());
            records.add(record);
        });

        //start container + listener
        container.start();

        //wait until container has required number of partitions
        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
    }

    @After
    public void tearDown() {
        container.stop();
    }

    @Test
    public void test_publish_ProductACreatedEvent() throws InterruptedException {
        //publish
        ProductADto prod = new ProductADto();
        prod.setAccountId(1L);
        prod.setCharge(Money.usd(BigDecimal.valueOf(1000L)));
        prod.setSetupCharge(Money.usd(BigDecimal.valueOf(200L)));

        productAProducer.publishProductACreatedEvent(prod);

        //check message was received
        ConsumerRecord<String, ProductAEventMessage> received = records.poll(10, TimeUnit.SECONDS);

        assertNotNull(received);
        log.debug("message received: " + received.toString());

        ProductAEventMessage event = received.value();

        assertNotNull(event);
        assertEquals(ProductAEventType.PRODUCT_A_CREATED, event.getEvent());
        assertEquals(event.getPayload().getAccountId(), prod.getAccountId());
    }

    @Test
    public void test_publish_ProductAUpdatedEvent() throws InterruptedException {
        //publish
        ProductADto prod = new ProductADto();
        prod.setAccountId(1L);
        prod.setCharge(Money.usd(BigDecimal.valueOf(1200L)));
        prod.setSetupCharge(Money.usd(BigDecimal.valueOf(300L)));

        productAProducer.publishProductAUpdatedEvent(prod);

        //check message was received
        ConsumerRecord<String, ProductAEventMessage> received = records.poll(10, TimeUnit.SECONDS);

        assertNotNull(received);
        log.debug("message received: " + received.toString());

        ProductAEventMessage event = received.value();

        assertNotNull(event);
        assertEquals(ProductAEventType.PRODUCT_A_UPDATED, event.getEvent());
        assertEquals(event.getPayload().getAccountId(), prod.getAccountId());
    }

}
