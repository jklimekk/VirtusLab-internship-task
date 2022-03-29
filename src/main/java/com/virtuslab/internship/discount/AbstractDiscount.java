package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;

public abstract class AbstractDiscount implements Discount {
    @Override
    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(getValue());
            var discounts = receipt.discounts();
            discounts.add(this.getName());
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    @Override
    public abstract String getName();

    protected abstract BigDecimal getValue();

    protected abstract boolean shouldApply(Receipt receipt);
}
