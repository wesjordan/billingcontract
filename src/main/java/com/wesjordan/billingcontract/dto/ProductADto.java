package com.wesjordan.billingcontract.dto;

import com.wesjordan.billingcontract.domain.BillingFrequency;
import com.wesjordan.billingcontract.domain.Money;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ProductADto {

    private Long id;
    private Long accountId;
    private Money charge;
    private Money setupCharge;
    private Date startDate;
    private Integer contractLength;
    private BillingFrequency billingFrequency;

}
