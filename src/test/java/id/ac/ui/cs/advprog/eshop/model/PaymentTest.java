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

    @Test
    void paymentConstructor_createsPaymentWithAllFields() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "Bank A");
        
        Product product = new Product("Test Product", 100);
        List<Product> products = Arrays.asList(product);
        Order order = new Order("order-2", products, System.currentTimeMillis(), "author");

        Payment payment = new Payment(
                "pay-2",
                "BANK_TRANSFER",
                "PENDING",
                paymentData,
                order
        );

        assertEquals("pay-2", payment.getId());
        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("Bank A", payment.getPaymentData().get("bankName"));
        assertEquals(order, payment.getOrder());
    }

    @Test
    void paymentConstructor_withNullFields_createsPaymentWithNullFields() {
        Payment payment = new Payment(
                "pay-3",
                null,
                null,
                null,
                null
        );

        assertEquals("pay-3", payment.getId());
        assertNull(payment.getMethod());
        assertNull(payment.getStatus());
        assertNull(payment.getPaymentData());
        assertNull(payment.getOrder());
    }

    @Test
    void setStatus_updatesStatusField() {
        Payment payment = Payment.builder()
                .id("pay-4")
                .status("PENDING")
                .build();

        assertEquals("PENDING", payment.getStatus());

        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void setStatus_canChangeToFailed() {
        Payment payment = Payment.builder()
                .id("pay-6")
                .status("SUCCESS")
                .build();

        payment.setStatus("FAILED");

        assertEquals("FAILED", payment.getStatus());
    }

    @Test
    void setStatus_canChangeFromRejectedToSuccess() {
        Payment payment = Payment.builder()
                .id("pay-5")
                .status("REJECTED")
                .build();

        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }
}
