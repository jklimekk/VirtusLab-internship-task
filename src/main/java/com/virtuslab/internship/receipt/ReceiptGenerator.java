package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        List<Product> products = basket.getProducts();

        Map<Product, Integer> map = products.stream()
                .collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum));

        for(Map.Entry<Product, Integer> item : map.entrySet()) {
            receiptEntries.add(new ReceiptEntry(item.getKey(), item.getValue()));
        }

        return new Receipt(receiptEntries);
    }
}
