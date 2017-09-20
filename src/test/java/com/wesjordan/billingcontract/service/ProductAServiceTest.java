package com.wesjordan.billingcontract.service;


import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.repository.ProductARepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class ProductAServiceTest {

    @TestConfiguration
    static class ProductAServiceTestContextConfiguration {

        @Bean
        public ProductAService productAService(){
            return new ProductAService();
        }
    }

    @Autowired
    private ProductAService productAService;

    @MockBean
    private ProductARepository productARepository;

    @Before
    public void setUp(){

    }

    @Test
    public void testGetProductAWithSpecificAccountId(){
        //given
        ProductA testProductA = getProductA();
        given(this.productAService.getProductByAccountId(1l)).willReturn(testProductA);

        //when
        ProductA p = productAService.getProductByAccountId(1l);

        //then
        assertThat(p.getAccountId().equals(testProductA.getAccountId()));
        assertThat(p.getContractLength().equals(testProductA.getContractLength()));
        assertThat(p.getCharge().equals(testProductA.getCharge()));
    }

    private ProductA getProductA(){
        ProductA productA = new ProductA();
        productA.setAccountId(1l);
        productA.setContractLength(3);
        productA.setCharge(BigDecimal.valueOf(3500));
        productA.setSetupCharge(BigDecimal.valueOf(1400));
        productA.setStartDate(new Date());
        return productA;
    }
}
