package com.wesjordan.billingcontract.service;


import com.wesjordan.billingcontract.domain.Money;
import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.mapping.ProductAMapper;
import com.wesjordan.billingcontract.stream.producer.ProductAProducer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ProductAServiceTestConfig.class)
public class ProductAServiceTest {

    @Mock
    private ProductAProducer productAProducer;

    @Mock
    private ProductAMapper productAMapper;

    @Autowired
    private ProductAMapper mapper;

    @InjectMocks
    private ProductAService productAService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetProductAWithSpecificAccountId(){
        //given
        ProductADto testProductA = getProductA1();
        given(this.productAService.getProductByAccountId(1L)).willReturn(testProductA);

        //when
        ProductADto p = productAService.getProductByAccountId(1L);

        //then
        assertEquals(p.getAccountId(),testProductA.getAccountId());
        assertEquals(p.getContractLength(),testProductA.getContractLength());
        assertEquals(p.getCharge(),testProductA.getCharge());
    }

    @Test
    public void testGetAllProductA(){
        //given
        Iterable<ProductADto> testProductAList = getProductAList();
        given(this.productAService.getAllProducts()).willReturn(testProductAList);

        //when
        Iterable<ProductADto> productAIterable = productAService.getAllProducts();

        //then
        assertEquals(productAIterable, testProductAList);
    }

    @Test
    public void testAddProductA(){
        //given
        ProductADto testProductA = getProductA1();

        ProductA m = mapper.map(testProductA, ProductA.class);

        given(this.productAMapper.map(testProductA, ProductA.class)).willReturn(m);
        given(this.productAMapper.map(m, ProductADto.class)).willReturn(testProductA);

        //when
        productAService.addProductA(testProductA);

        verify(productAProducer, times(1)).publishProductACreatedEvent(testProductA);

    }

    private ProductADto getProductA1() {
        return getProductA(1L, 3, BigDecimal.valueOf(3500), BigDecimal.valueOf(1400));
    }

    private ProductADto getProductA2() {
        return getProductA(2L, 6, BigDecimal.valueOf(4500), BigDecimal.valueOf(2000));
    }

    private Iterable<ProductADto> getProductAList() {
        List<ProductADto> productAS = new ArrayList<>();
        productAS.add(getProductA1());
        productAS.add(getProductA2());

        return productAS;
    }

    private ProductADto getProductA(long accountId, int contractLength, BigDecimal charge, BigDecimal setupCharge) {
        ProductADto productA = new ProductADto();
        productA.setAccountId(accountId);
        productA.setContractLength(contractLength);
        productA.setCharge(Money.usd(charge));
        productA.setSetupCharge(Money.usd(setupCharge));
        productA.setStartDate(new Date());
        return productA;
    }
}
