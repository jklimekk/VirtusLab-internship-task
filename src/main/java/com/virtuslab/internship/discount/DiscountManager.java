package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

import java.util.ArrayList;
import java.util.List;

public class DiscountManager {
    private final List<Discount> discounts = new ArrayList<>(List.of(new FifteenPercentDiscount(),
                                                                            new TenPercentDiscount()));

    public Receipt apply(Receipt receipt) {
        for(Discount discount : discounts) {
            receipt = discount.apply(receipt);
        }

        return receipt;
    }


}
