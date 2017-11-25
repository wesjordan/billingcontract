package com.wesjordan.billingcontract.controller;

import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.service.ProductAService;
import com.wesjordan.billingcontract.validation.ProductAValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/productA")
public class ProductAController {

    private ProductAService productAService;
    private ProductAValidator productAValidator;

    @Autowired
    public ProductAController(ProductAService productAService, ProductAValidator productAValidator){
        this.productAService = productAService;
        this.productAValidator = productAValidator;
    }

    @ApiOperation(value = "Returns a list of all ProductA's")
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Iterable<ProductADto> getProducts() {
        log.info("getProducts()");
        return productAService.getAllProducts();
    }

    @ApiOperation(value = "Returns a particular ProductA based on the accountId")
    @RequestMapping(method = RequestMethod.GET, value = "/{accountId}")
    public ProductADto getProductByAccountId(@PathVariable Long accountId) {
        log.info("getProductByAccountId({})", accountId);
        return productAService.getProductByAccountId(accountId);
    }

    @ApiOperation(value = "Adds a new instance of ProductA")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 502, message = "Bad Gateway")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public void addProduct(@Valid @RequestBody ProductADto productA) {
        log.info("addProduct({})", productA);
        productAService.addProductA(productA);
    }

    @ApiOperation(value = "Updates an instance of ProductA")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 502, message = "Bad Gateway")
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/")
    public void updateProduct(@Valid @RequestBody ProductADto productA) {
        log.info("updateProduct({})", productA);
        productAService.updateProductA(productA);
    }

    @InitBinder
    public void dataBinding(WebDataBinder dataBinder){
        dataBinder.addValidators(productAValidator);
    }

}
