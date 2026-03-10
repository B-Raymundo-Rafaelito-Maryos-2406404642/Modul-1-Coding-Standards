package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentServiceImpl service;

    private Order makeOrder() {
        Product p = new Product("A", 1);
        List<Product> products = Arrays.asList(p);
        return new Order("ord-1", products, System.currentTimeMillis(), "author");
    }
    
    private Order makeOrderWithNullId() {
        Product p = new Product("A", 1);
        List<Product> products = Arrays.asList(p);
        // Use reflection to set id to null
        Order order = new Order("ord-temp", products, System.currentTimeMillis(), "author");
        try {
            java.lang.reflect.Field field = Order.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(order, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Test
    void addPayment_voucher_valid_setsSuccess_andUpdatesOrder() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "VOUCHER", data);

        assertNotNull(p);
        assertEquals("SUCCESS", p.getStatus());
        assertEquals("SUCCESS", order.getStatus());
        verify(paymentRepository).save(p);
        verify(orderRepository).save(order);
    }

    @Test
    void addPayment_voucher_invalid_setsRejected_andOrderFailed() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "INVALIDCODE123456");

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "VOUCHER", data);

        assertNotNull(p);
        assertEquals("REJECTED", p.getStatus());
        assertEquals("FAILED", order.getStatus());
        verify(paymentRepository).save(p);
        verify(orderRepository).save(order);
    }

    @Test
    void addPayment_bankTransfer_missingInfo_rejected_andOrderFailed() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "Bank A");
        data.put("referenceCode", "");

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "BANK_TRANSFER", data);

        assertNotNull(p);
        assertEquals("REJECTED", p.getStatus());
        assertEquals("FAILED", order.getStatus());
        verify(paymentRepository).save(p);
        verify(orderRepository).save(order);
    }

    @Test
    void addPayment_nullOrder_returnsNull() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        Payment result = service.addPayment(null, "VOUCHER", data);

        assertNull(result);
        verify(paymentRepository, never()).save(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void addPayment_nullMethod_paymentStatusIsPending() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("key", "value");

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, null, data);

        assertNotNull(p);
        assertEquals("PENDING", p.getStatus());
        verify(paymentRepository).save(p);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void addPayment_bankTransfer_valid_setsSuccess_andUpdatesOrder() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "Bank Central Asia");
        data.put("referenceCode", "REF123456789");

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "BANK_TRANSFER", data);

        assertNotNull(p);
        assertEquals("SUCCESS", p.getStatus());
        assertEquals("SUCCESS", order.getStatus());
        verify(paymentRepository).save(p);
        verify(orderRepository).save(order);
    }

    @Test
    void addPayment_voucher_nullPaymentData_setsRejected_andOrderFailed() {
        Order order = makeOrder();

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "VOUCHER", null);

        assertNotNull(p);
        assertEquals("REJECTED", p.getStatus());
        assertEquals("FAILED", order.getStatus());
        verify(paymentRepository).save(p);
        verify(orderRepository).save(order);
    }

    @Test
    void addPayment_voucher_nullVoucherCode_setsRejected_andOrderFailed() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", null);

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "VOUCHER", data);

        assertNotNull(p);
        assertEquals("REJECTED", p.getStatus());
        assertEquals("FAILED", order.getStatus());
        verify(paymentRepository).save(p);
        verify(orderRepository).save(order);
    }

    @Test
    void addPayment_voucher_invalidLength_setsRejected_andOrderFailed() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHORT12"); // Less than 16 chars

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "VOUCHER", data);

        assertNotNull(p);
        assertEquals("REJECTED", p.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void addPayment_voucher_notStartingWithESHOP_setsRejected_andOrderFailed() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "NOTESHOP12345678"); // Does not start with ESHOP

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "VOUCHER", data);

        assertNotNull(p);
        assertEquals("REJECTED", p.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void addPayment_voucher_lessThan8Digits_setsRejected_andOrderFailed() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABCDEFG"); // Less than 8 digits

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "VOUCHER", data);

        assertNotNull(p);
        assertEquals("REJECTED", p.getStatus());
        assertEquals("FAILED", order.getStatus());
    }
    
    @Test
    void addPayment_voucher_emptyVoucherCode_setsRejected_andOrderFailed() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", ""); // Empty string

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "VOUCHER", data);

        assertNotNull(p);
        assertEquals("REJECTED", p.getStatus());
        assertEquals("FAILED", order.getStatus());
    }
    
    @Test
    void addPayment_bankTransfer_emptyBankName_rejected() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "");
        data.put("referenceCode", "REF123");

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "BANK_TRANSFER", data);

        assertNotNull(p);
        assertEquals("REJECTED", p.getStatus());
        assertEquals("FAILED", order.getStatus());
    }
    
    @Test
    void addPayment_bankTransfer_emptyReferenceCode_rejected() {
        Order order = makeOrder();
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "Bank BCA");
        data.put("referenceCode", "");

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment p = service.addPayment(order, "BANK_TRANSFER", data);

        assertNotNull(p);
        assertEquals("REJECTED", p.getStatus());
        assertEquals("FAILED", order.getStatus());
    }
    
    @Test
    void addPayment_orderWithNullId_returnsNull() {
        Order order = makeOrderWithNullId();
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        Payment result = service.addPayment(order, "VOUCHER", data);

        assertNull(result);
        verify(paymentRepository, never()).save(any());
        verify(orderRepository, never()).save(any());
    }
    

    @Test
    void setStatus_nullPayment_returnsNull() {
        Payment result = service.setStatus(null, "SUCCESS");

        assertNull(result);
        verify(paymentRepository, never()).save(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void setStatus_nullStatus_returnsNull() {
        Payment payment = Payment.builder()
                .id("pay-1")
                .status("PENDING")
                .build();

        Payment result = service.setStatus(payment, null);

        assertNull(result);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void setStatus_successStatus_updatesPaymentAndOrder() {
        Order order = makeOrder();
        Payment payment = new Payment("pay-1", "VOUCHER", "PENDING", null, order);

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment result = service.setStatus(payment, "SUCCESS");

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", order.getStatus());
        verify(paymentRepository).save(payment);
        verify(orderRepository).save(order);
    }

    @Test
    void setStatus_rejectedStatus_updatesPaymentAndOrder() {
        Order order = makeOrder();
        Payment payment = new Payment("pay-2", "BANK_TRANSFER", "PENDING", null, order);

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment result = service.setStatus(payment, "REJECTED");

        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
        assertEquals("FAILED", order.getStatus());
        verify(paymentRepository).save(payment);
        verify(orderRepository).save(order);
    }

    @Test
    void setStatus_paymentWithNullOrder_updatesPaymentOnly() {
        Payment payment = new Payment("pay-3", "VOUCHER", "PENDING", null, null);

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment result = service.setStatus(payment, "SUCCESS");

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository).save(payment);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void setStatus_otherStatus_updatesPaymentButNotOrder() {
        Order order = makeOrder();
        Payment payment = new Payment("pay-4", "VOUCHER", "PENDING", null, order);

        when(paymentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Payment result = service.setStatus(payment, "CANCELLED");

        assertNotNull(result);
        assertEquals("CANCELLED", result.getStatus());
        // Status should remain as original for non-SUCCESS/REJECTED
        assertNotEquals("SUCCESS", order.getStatus());
        assertNotEquals("FAILED", order.getStatus());
        verify(paymentRepository).save(payment);
        verify(orderRepository, never()).save(any());
    }
}
