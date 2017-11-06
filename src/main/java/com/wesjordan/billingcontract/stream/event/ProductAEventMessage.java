package com.wesjordan.billingcontract.stream.event;

import com.wesjordan.billingcontract.dto.ProductADto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAEventMessage {
    private ProductAEventType event;
    private ProductADto payload;
}
