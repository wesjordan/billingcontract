package com.wesjordan.billingcontract.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Currency;

@RunWith(SpringRunner.class)
public class MeanBeanTest {

    @Before
    public void setup(){

    }

    @Test
    public void testProductA(){
        BeanTester beanTester = new BeanTester();
        Configuration config = new ConfigurationBuilder()
                .overrideFactory("charge", new MoneyFactory())
                .overrideFactory("setupCharge", new MoneyFactory()).build();
        beanTester.testBean(ProductA.class, config);
    }

    @Test
    public void testMoney(){
        BeanTester beanTester = new BeanTester();
        Configuration config = new ConfigurationBuilder().overrideFactory("currency", new CurrencyFactory()).build();
        beanTester.testBean(Money.class, config);
    }

    private class MoneyFactory implements Factory<Money> {
        @Override
        public Money create() {
            return Money.usd(BigDecimal.valueOf(500L));
        }
    }

    private class CurrencyFactory implements Factory<Currency> {
        @Override
        public Currency create() {
            return Currency.getInstance("USD");
        }
    }
}
