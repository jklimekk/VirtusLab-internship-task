package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public class TenPercentDiscount extends AbstractDiscount {

    public static final String NAME = "TenPercentDiscount";
    private static final BigDecimal value = BigDecimal.valueOf(0.9);

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected BigDecimal getValue() {
        return value;
    }

    @Override
    protected boolean shouldApply(Receipt receipt) {
        return receipt.totalPrice().compareTo(BigDecimal.valueOf(50)) > 0;
    }
}
