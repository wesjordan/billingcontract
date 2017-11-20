package com.wesjordan.billingcontract.service;

import com.wesjordan.billingcontract.domain.ProductA;
import com.wesjordan.billingcontract.dto.ProductADto;
import com.wesjordan.billingcontract.mapping.ProductAMapper;
import com.wesjordan.billingcontract.repository.ProductARepository;
import com.wesjordan.billingcontract.stream.producer.ProductAProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductAService {

    private ProductARepository productARepository;
    private ProductAProducer productAProducer;
    private ProductAMapper productAMapper;

    @Autowired
    public ProductAService(ProductARepository productARepository, ProductAProducer productAProducer, ProductAMapper productAMapper){
        this.productARepository = productARepository;
        this.productAProducer = productAProducer;
        this.productAMapper = productAMapper;
    }

    public void addProductA(ProductADto productADto){
        productAProducer.publishProductACreatedEvent(productADto);
    }

    public void updateProductA(ProductADto productADto){
        productAProducer.publishProductAUpdatedEvent(productADto);
    }


    public void saveProductA(ProductA productA) {
        productARepository.save(productA);
    }

    public Iterable<ProductADto> getAllProducts(){
        return productAMapper.mapAsList(productARepository.findAll(), ProductADto.class);
    }

    public ProductADto getProductByAccountId(Long accountId){
        return productAMapper.map(productARepository.findByAccountId(accountId), ProductADto.class);
    }


}
