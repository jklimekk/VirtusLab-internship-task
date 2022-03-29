package com.virtuslab.internship.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getReceiptWithCorrectDiscounts() throws Exception {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cheese = productDb.getProduct("Cheese");
        var onion = productDb.getProduct("Onion");

        var basket = new Basket();
        basket.addProduct(bread);
        basket.addProduct(bread);
        basket.addProduct(bread);
        basket.addProduct(cheese);
        basket.addProduct(onion);

        var expectedTotalPrice = bread.price()
                .multiply(BigDecimal.valueOf(3))
                .add(cheese.price())
                .add(onion.price())
                .multiply(BigDecimal.valueOf(0.85));

        // When
        MvcResult result = this.mockMvc.perform(post("/basket/receipt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(basket)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Receipt receipt = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Receipt.class);

        // Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
    }

    @Test
    void getReceiptForEmptyBasket() throws Exception {
        // Given
        var basket = new Basket();
        var expectedTotalPrice = BigDecimal.ZERO;

        // When
        MvcResult result = this.mockMvc.perform(post("/basket/receipt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(basket)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Receipt receipt = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Receipt.class);

        // Then
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }

}