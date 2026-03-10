package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void paymentBuilder_createsPaymentWithAllFields() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("key", "value");
        
        Product product = new Product("Test Product", 100);
        List<Product> products = Arrays.asList(product);
        Order order = new Order("order-1", products, System.currentTimeMillis(), "author");

        Payment payment = Payment.builder()
                .id("pay-1")
                .method("VOUCHER")
                .status("SUCCESS")
                .paymentData(paymentData)
                .order(order)
                .build();

        assertEquals("pay-1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("value", payment.getPaymentData().get("key"));
        assertEquals(order, payment.getOrder());
    }
}
