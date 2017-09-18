package com.wesjordan.billingcontract.service;

import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.repository.ProductARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductAService {

    @Autowired
    ProductARepository productARepository;

    public void addProductA(ProductA productA){
        productARepository.save(productA);
    }

    public Iterable<ProductA> getAllProducts(){
        return productARepository.findAll();
    }

    public ProductA getProductByAccountId(Long accountId){
        return productARepository.findByAccountId(accountId);
    }
}
