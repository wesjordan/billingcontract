package com.wesjordan.billingcontract.service;

import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.repository.ProductARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductAService {

    private ProductARepository productARepository;

    @Autowired
    public ProductAService(ProductARepository productARepository){
        this.productARepository = productARepository;
    }


    public ProductA addProductA(ProductA productA){
        return productARepository.save(productA);
    }

    public Iterable<ProductA> getAllProducts(){
        return productARepository.findAll();
    }

    public ProductA getProductByAccountId(Long accountId){
        return productARepository.findByAccountId(accountId);
    }
}
