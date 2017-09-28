package com.wesjordan.billingcontract.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesjordan.billingcontract.BillingcontractApplication;
import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.repository.ProductARepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingcontractApplication.class)
@WebAppConfiguration
public class ProductAControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ProductARepository productARepository;


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
        ProductA newProduct = new ProductA();
        newProduct.setAccountId(1L);
        newProduct.setContractLength(3);

        productARepository.save(newProduct);

        //when + then
        mockMvc.perform(get("/productA/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId", is(1)))
                .andExpect(jsonPath("$.contractLength", is(3)));
    }


    @Test
    public void getAllProductATest() throws Exception{
        //given
        ProductA productA1 = new ProductA();
        productA1.setAccountId(1L);
        productA1.setContractLength(3);

        ProductA productA2 = new ProductA();
        productA2.setAccountId(2L);
        productA2.setContractLength(6);

        productARepository.save(productA1);
        productARepository.save(productA2);

        //when + then
        mockMvc.perform(get("/productA/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountId", is(1)));
    }

    @Test
    public void addProductATest() throws Exception{
        //given
        ProductA productA = new ProductA();
        productA.setAccountId(1L);
        productA.setContractLength(3);

        //when + then
        mockMvc.perform(post("/productA/").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productA)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId", is(1)));

    }

}
