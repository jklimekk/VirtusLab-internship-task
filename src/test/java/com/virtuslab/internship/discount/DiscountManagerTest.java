package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscountManagerTest {

    @Test
    void shouldApply15PercentDiscountNot10PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cereals, 2));

        var receipt = new Receipt(receiptEntries);
        var discountManager = new DiscountManager();
        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(2))
                .add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = discountManager.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApply10PercentDiscountNot15PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));
        receiptEntries.add(new ReceiptEntry(cheese, 2));

        var receipt = new Receipt(receiptEntries);
        var discountManager = new DiscountManager();
        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(2))
                .add(cheese.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = discountManager.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply10PercentDiscountNor15PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cheese, 2));

        var receipt = new Receipt(receiptEntries);
        var discountManager = new DiscountManager();
        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(1))
                .add(cheese.price().multiply(BigDecimal.valueOf(2)));

        // When
        var receiptAfterDiscount = discountManager.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply10PercentDiscountAfterApplying15PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 3));
        receiptEntries.add(new ReceiptEntry(cheese, 2));

        var receipt = new Receipt(receiptEntries);
        var discountManager = new DiscountManager();
        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(3))
                .add(cheese.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = discountManager.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApply10PercentDiscountAndApplying15PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cheese = productDb.getProduct("Cheese");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 3));
        receiptEntries.add(new ReceiptEntry(cheese, 3));

        var receipt = new Receipt(receiptEntries);
        var discountManager = new DiscountManager();
        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(3))
                .add(cheese.price().multiply(BigDecimal.valueOf(3)))
                .multiply(BigDecimal.valueOf(0.85))
                .multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = discountManager.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }
}