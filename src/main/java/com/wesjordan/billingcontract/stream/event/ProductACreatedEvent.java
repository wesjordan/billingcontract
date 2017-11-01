package com.wesjordan.billingcontract.stream.event;

import com.wesjordan.billingcontract.dto.ProductADto;
import lombok.Getter;

@Getter
public class ProductACreatedEvent implements ProductAEvent{

    private ProductAEventType event = ProductAEventType.PRODUCT_A_CREATED;
    private ProductADto payload;

    public ProductACreatedEvent(ProductADto payload){
        this.payload = payload;
    }

}
