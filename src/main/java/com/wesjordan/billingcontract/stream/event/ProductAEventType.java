package com.wesjordan.billingcontract.stream.event;

public enum ProductAEventType {

    PRODUCT_A_CREATED("PRODUCT_A_CREATED"), PRODUCT_A_UPDATED("PRODUCT_A_UPDATED");

    private String eventType;

    ProductAEventType(String eventType) { this.eventType = eventType;}
}
