package com.wesjordan.billingcontract.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name="product_a")
public class ProductA {

    @Id @GeneratedValue
    private Long id;

    private Long accountId;
    private BigDecimal charge;
    private BigDecimal setupCharge;
    private Date startDate;
    private Integer contractLength;

    @Column(name = "billing_frequency")
    private BillingFrequency billingFrequency;
}
