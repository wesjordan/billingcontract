package com.wesjordan.billingcontract.domain;

public enum BillingFrequency {

    MONTHLY(1), QUARTERLY(3), ANNUALLY(12);

    private Integer billingFrequency;

    BillingFrequency(Integer billingFrequency){
        this.billingFrequency = billingFrequency;
    }
}
