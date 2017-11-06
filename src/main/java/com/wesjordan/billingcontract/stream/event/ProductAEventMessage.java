package com.wesjordan.billingcontract.stream.event;

import com.wesjordan.billingcontract.dto.ProductADto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductAEventMessage {
    private ProductAEventType event;
    private ProductADto payload;

    public ProductAEventMessage(ProductAEventType event, ProductADto payload) {
        this.event = event;
        this.payload = payload;
    }
}
