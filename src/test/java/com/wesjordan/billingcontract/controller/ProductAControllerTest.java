package com.wesjordan.billingcontract.controller;

import com.wesjordan.billingcontract.BillingcontractApplication;
import com.wesjordan.billingcontract.repository.ProductARepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillingcontractApplication.class)
@WebAppConfiguration
public class ProductAControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ProductARepository productARepository;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setup() throws Exception{
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void getAllProductATest() throws Exception{
        mockMvc.perform(get("/productA/"))
                .andExpect(status().isOk());
    }


}
