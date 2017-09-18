package com.wesjordan.billingcontract.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
}
