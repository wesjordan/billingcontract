package com.wesjordan.billingcontract.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="product_a")
public class ProductA {

    @Id @GeneratedValue
    private Long id;
    private Long accountId;
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name="value", column = @Column(name = "charge_value")),
            @AttributeOverride(name="currency", column = @Column(name = "charge_currency")),
    })
    private Money charge;
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name="value", column = @Column(name = "setup_charge_value")),
            @AttributeOverride(name="currency", column = @Column(name = "setup_charge_currency")),
    })
    private Money setupCharge;
    private Date startDate;
    private Integer contractLength;

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_frequency")
    private BillingFrequency billingFrequency;
}
