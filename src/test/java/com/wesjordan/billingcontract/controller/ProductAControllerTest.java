package com.wesjordan.billingcontract.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesjordan.billingcontract.BillingcontractApplication;
import com.wesjordan.billingcontract.domain.BillingFrequency;
import com.wesjordan.billingcontract.domain.Money;
import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.mapping.ProductAMapper;
import com.wesjordan.billingcontract.repository.ProductARepository;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingcontractApplication.class)
@WebAppConfiguration
@DirtiesContext
public class ProductAControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ProductARepository productARepository;

    @Autowired
    private ProductAMapper productAMapper;

    private static String PUBLISHING_TOPIC = "ProductA";

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, PUBLISHING_TOPIC);

    @Before
    public void setup() throws Exception{
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception{
        productARepository.deleteAll();
    }

    @Test
    public void getProductAByAccountIdTest() throws Exception{
        //given
        ProductADto newProduct = new ProductADto();
        newProduct.setAccountId(1L);
        newProduct.setContractLength(3);

//        productARepository.save(productAMapper.map(newProduct, ProductA.class));
//
//        //when + then
//        mockMvc.perform(get("/productA/1")).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accountId", is(1)))
//                .andExpect(jsonPath("$.contractLength", is(3)));
    }


    @Test
    public void getAllProductATest() throws Exception{
        //given
        ProductADto productA1 = new ProductADto();
        productA1.setAccountId(1L);
        productA1.setContractLength(3);
        productA1.setCharge(Money.eur(BigDecimal.valueOf(4500L)));
        productA1.setSetupCharge(Money.eur(BigDecimal.valueOf(1500L)));

        ProductADto productA2 = new ProductADto();
        productA2.setAccountId(2L);
        productA2.setContractLength(6);
        productA2.setCharge(Money.eur(BigDecimal.valueOf(4500L)));
        productA2.setSetupCharge(Money.eur(BigDecimal.valueOf(1500L)));

//        productARepository.save(productAMapper.map(productA1, ProductA.class));
//        productARepository.save(productAMapper.map(productA2, ProductA.class));
//
//        //when + then
//        mockMvc.perform(get("/productA/")).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].accountId", is(1)));
    }

    @Test
    public void addProductATest() throws Exception{
        //given
        ProductADto productA = new ProductADto();
        productA.setAccountId(1L);
        productA.setContractLength(3);
        productA.setBillingFrequency(BillingFrequency.MONTHLY);
        productA.setCharge(Money.eur(BigDecimal.valueOf(10000L)));
        productA.setSetupCharge(Money.eur(BigDecimal.valueOf(1500L)));
        productA.setStartDate(new Date());

//        //when + then
//        mockMvc.perform(post("/productA/").contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(productA)))
//                .andDo(print())
//                .andExpect(status().isOk());
    }

    @Test
    public void updateProductATest() throws Exception {
        //given
        ProductADto productA = new ProductADto();
        productA.setAccountId(1L);
        productA.setContractLength(3);
        productA.setBillingFrequency(BillingFrequency.MONTHLY);
        productA.setCharge(Money.eur(BigDecimal.valueOf(10000L)));
        productA.setSetupCharge(Money.eur(BigDecimal.valueOf(1500L)));
        productA.setStartDate(new Date());

//        //when + then
//        mockMvc.perform(put("/productA/").contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(productA)))
//                .andDo(print())
//                .andExpect(status().isOk());
    }

    @Test
    public void testValidationOnAddProductAWithInvalidAccountId() throws Exception{
        //given
        ProductADto productA = new ProductADto();
        productA.setAccountId(0L);

        //when + then
//        mockMvc.perform(post("/productA/").contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(productA)))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.errorCode", is("Validation error")))
//                .andExpect(jsonPath("$.errorMessage", is("Invalid input")))
//                .andExpect(jsonPath("$.errors[0]", is("The supplied accountId is invalid")));
//
//
//        //given
//        productA.setAccountId(null);
//
//        //when + then
//        mockMvc.perform(post("/productA/").contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(productA)))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.errorCode", is("Validation error")))
//                .andExpect(jsonPath("$.errorMessage", is("Invalid input")))
//                .andExpect(jsonPath("$.errors[0]", is("The supplied accountId is invalid")));
    }

}
