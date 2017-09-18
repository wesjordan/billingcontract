package com.wesjordan.billingcontract.controller;

import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.service.ProductAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/productA")
public class ProductAController {

    @Autowired
    ProductAService productAService;

    @PostConstruct
    public void setupProduct(){
        ProductA productA = new ProductA();
        productA.setAccountId(1l);
        productA.setCharge(BigDecimal.valueOf(3500l));
        productA.setSetupCharge(BigDecimal.valueOf(1000l));
        productA.setStartDate(new Date());
        productA.setContractLength(3);
        productAService.addProductA(productA);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Iterable<ProductA> getProducts(){
        return productAService.getAllProducts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{accountId}")
    public ProductA getProductByAccountId(@PathVariable Long accountId){
        return productAService.getProductByAccountId(accountId);
    }
}
