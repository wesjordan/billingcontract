package com.wesjordan.billingcontract.controller;

import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.service.ProductAService;
import com.wesjordan.billingcontract.validation.ProductAValidator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/productA")
public class ProductAController {

    @Autowired
    ProductAService productAService;

    @Autowired
    ProductAValidator productAValidator;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Iterable<ProductA> getProducts(){
        return productAService.getAllProducts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{accountId}")
    public ProductA getProductByAccountId(@PathVariable Long accountId){
        return productAService.getProductByAccountId(accountId);
    }

    @ApiOperation(value = "Adds a new instance of ProductA")
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ProductA addProduct(@Valid @RequestBody ProductA productA){
        return productAService.addProductA(productA);
    }

    @InitBinder
    public void dataBinding(WebDataBinder dataBinder){
        dataBinder.addValidators(productAValidator);
    }

}
