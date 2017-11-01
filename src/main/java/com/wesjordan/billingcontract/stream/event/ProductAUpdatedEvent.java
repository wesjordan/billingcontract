package com.wesjordan.billingcontract.stream.event;

import com.wesjordan.billingcontract.dto.ProductADto;
import lombok.Getter;

@Getter
public class ProductAUpdatedEvent implements ProductAEvent {

    private ProductAEventType event = ProductAEventType.PRODUCT_A_UPDATED;
    private ProductADto payload;

    public ProductAUpdatedEvent(ProductADto payload){
        this.payload = payload;
    }
}
