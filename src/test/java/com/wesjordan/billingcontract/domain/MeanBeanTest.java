package com.wesjordan.billingcontract.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;

@RunWith(SpringRunner.class)
public class MeanBeanTest {

    @Before
    public void setup(){

    }

    @Test
    public void testProductA(){
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(ProductA.class);
        beanTester.testBean(Money.class);
    }

    @Test
    public void testMoney(){
        BeanTester beanTester = new BeanTester();
        Configuration config = new ConfigurationBuilder().overrideFactory("currency", new CurrencyFactory()).build();
        beanTester.testBean(Money.class, config);
    }


    private class CurrencyFactory implements Factory<Currency> {

        @Override
        public Currency create() {
            return  Currency.getInstance("USD");
        }
    }
}
