package com.wesjordan.billingcontract.domain;

public enum BillingFrequency {

    MONTHLY(1), QUARTERLY(3), ANNUALLY(12);

    private Integer frequency;

    BillingFrequency(Integer frequency) {
        this.frequency = frequency;
    }
}
