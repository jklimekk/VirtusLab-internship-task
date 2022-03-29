package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;

public class FifteenPercentDiscount extends AbstractDiscount {

    public static final String NAME = "FifteenPercentDiscount";
    private static final BigDecimal value = BigDecimal.valueOf(0.85);

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
        return receipt.entries()
                .stream()
                .filter(e -> e.product().type().equals(Product.Type.GRAINS))
                .mapToInt(ReceiptEntry::quantity)
                .sum() >= 3;
    }
}
