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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        ProductA testProductA = getProductA1();
        given(this.productAService.getProductByAccountId(1l)).willReturn(testProductA);

        //when
        ProductA p = productAService.getProductByAccountId(1l);

        //then
        assertThat(p.getAccountId().equals(testProductA.getAccountId()));
        assertThat(p.getContractLength().equals(testProductA.getContractLength()));
        assertThat(p.getCharge().equals(testProductA.getCharge()));
    }

    @Test
    public void testGetAllProductA(){
        //given
        Iterable<ProductA> testProductAList = getProductAllProductA();
        given(this.productAService.getAllProducts()).willReturn(testProductAList);

        //when
        Iterable<ProductA> productAIterable = productAService.getAllProducts();

        //then
        assertThat(productAIterable.equals(testProductAList));
    }

    @Test
    public void testAddProductA(){
        //given
        ProductA testProductA = getProductA1();
        given(this.productARepository.save(testProductA)).willReturn(null);

        //when
        productAService.addProductA(testProductA);

        verify(productARepository, times(1)).save(testProductA);
    }

    private ProductA getProductA1(){
        ProductA productA = getProductA(1l, 3, BigDecimal.valueOf(3500), BigDecimal.valueOf(1400));
        return productA;
    }

    private ProductA getProductA2(){
        ProductA productA = getProductA(2l, 6, BigDecimal.valueOf(4500), BigDecimal.valueOf(2000));
        return productA;
    }

    private Iterable<ProductA> getProductAllProductA(){
        List<ProductA> productAS = new ArrayList<>();
        productAS.add(getProductA1());
        productAS.add(getProductA2());

        return productAS;
    }

    private ProductA getProductA(long accountId, int contractLength, BigDecimal charge, BigDecimal setupCharge) {
        ProductA productA = new ProductA();
        productA.setAccountId(accountId);
        productA.setContractLength(contractLength);
        productA.setCharge(charge);
        productA.setSetupCharge(setupCharge);
        productA.setStartDate(new Date());
        return productA;
    }
}
