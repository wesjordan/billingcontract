package com.wesjordan.billingcontract.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.meanbean.test.BeanTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MeanBeanTest {

    @Test
    public void testExceptionResponse(){
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(ExceptionResponse.class);
    }
}
