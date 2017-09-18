package com.wesjordan.billingcontract.repository;

import com.wesjordan.billingcontract.domain.ProductA;
import org.springframework.data.repository.CrudRepository;

public interface ProductARepository extends CrudRepository<ProductA, Long> {

    Iterable<ProductA> findAll();

    ProductA findByAccountId(Long accountId);
}
