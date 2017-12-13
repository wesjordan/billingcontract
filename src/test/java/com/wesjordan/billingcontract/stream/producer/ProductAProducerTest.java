package com.wesjordan.billingcontract.stream.producer;

import com.wesjordan.billingcontract.domain.Money;
import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.repository.ProductARepository;
import com.wesjordan.billingcontract.stream.consumer.ProductAConsumer;
import com.wesjordan.billingcontract.stream.event.ProductAEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class ProductAProducerTest {

    private static String PUBLISHING_TOPIC = "ProductATest";

    @Autowired
    ProductAProducer productAProducer;

    @Autowired
    ProductAConsumer productAConsumer;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ProductARepository productARepository;

    private KafkaMessageListenerContainer<String, ProductAEventMessage> container;
    private BlockingQueue<ConsumerRecord<String, ProductAEventMessage>> records;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, PUBLISHING_TOPIC);

    @After
    public void tearDown() throws Exception {
        productARepository.deleteAll();
    }

    //@Test
    public void test_publish_ProductACreatedEvent() throws InterruptedException {
        //publish
        ProductADto prod = new ProductADto();
        prod.setAccountId(1L);
        prod.setCharge(Money.usd(BigDecimal.valueOf(1000L)));
        prod.setSetupCharge(Money.usd(BigDecimal.valueOf(200L)));

        log.info("Sending ProductA message to embedded kafka instance");
        productAProducer.publishProductACreatedEvent(prod);
        log.info("Sent ProductA message to embedded kafka instance");

        productAConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertEquals(0, productAConsumer.getLatch().getCount());
    }

    //@Test
    public void test_publish_ProductAUpdatedEvent() throws InterruptedException {
        //publish
        ProductADto prod = new ProductADto();
        prod.setAccountId(1L);
        prod.setCharge(Money.usd(BigDecimal.valueOf(1200L)));
        prod.setSetupCharge(Money.usd(BigDecimal.valueOf(300L)));

        log.info("Sending ProductA message to embedded kafka instance");
        productAProducer.publishProductAUpdatedEvent(prod);
        log.info("Sent ProductA message to embedded kafka instance");

        productAConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertEquals(0, productAConsumer.getLatch().getCount());
    }
}
