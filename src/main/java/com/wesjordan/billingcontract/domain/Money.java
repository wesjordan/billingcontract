package com.wesjordan.billingcontract.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
@Getter
public class Money {

    private BigDecimal value;
    private Currency currency;

    public Money(){
        //JPA
    }

    public Money(BigDecimal value, Currency currency) {
        this.value  = value;
        this.currency = currency;
    }

    public static Money usd(BigDecimal value) {
        final Money m;
        m = new Money(value, Currency.getInstance("USD"));
        return m;
    }

    public static Money eur(BigDecimal value) {
        final Money m;
        m = new Money(value, Currency.getInstance("EUR"));
        return m;
    }

}
