package com.wesjordan.billingcontract.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
@Data
public class Money {

    private BigDecimal value;
    private Currency currency;

    public Money(){
        //JPA
    }

    Money(BigDecimal value, Currency currency){
        this.value  = value;
        this.currency = currency;
    }

    public static Money USD(BigDecimal value){
        return new Money(value, Currency.getInstance("USD"));
    }

    public static Money EUR(BigDecimal value){
        return new Money(value, Currency.getInstance("EUR"));
    }

}
