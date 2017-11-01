package com.wesjordan.billingcontract.dto;

import com.wesjordan.billingcontract.domain.BillingFrequency;
import com.wesjordan.billingcontract.domain.Money;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class ProductADto {

    private Long accountId;
    private Money charge;
    private Money setupCharge;
    private Date startDate;
    private Integer contractLength;
    private BillingFrequency billingFrequency;

}
