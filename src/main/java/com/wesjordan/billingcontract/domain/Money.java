package com.wesjordan.billingcontract.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
@Getter
@Setter
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

    public static Money usd(BigDecimal value) {
        return new Money(value, Currency.getInstance("USD"));
    }

    public static Money eur(BigDecimal value) {
        return new Money(value, Currency.getInstance("EUR"));
    }

}
