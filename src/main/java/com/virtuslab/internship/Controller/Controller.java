package com.virtuslab.internship.Controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.DiscountManager;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
public class Controller {

    @PostMapping("/receipt")
    public Receipt getReceipt(@RequestBody Basket basket) {
        ReceiptGenerator receiptGenerator = new ReceiptGenerator();
        DiscountManager discountManager = new DiscountManager();

        Receipt receipt = receiptGenerator.generate(basket);
        receipt = discountManager.apply(receipt);

        return receipt;
    }
}
