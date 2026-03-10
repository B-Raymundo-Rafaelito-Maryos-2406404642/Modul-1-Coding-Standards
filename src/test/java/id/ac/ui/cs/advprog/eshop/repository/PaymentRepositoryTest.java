package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PaymentRepository();
    }

    @Test
    void save_withValidPayment_addsToRepository() {
        Payment payment = Payment.builder()
                .id("pay-1")
                .method("VOUCHER")
                .status("SUCCESS")
                .build();

        Payment saved = repository.save(payment);

        assertNotNull(saved);
        assertEquals("pay-1", saved.getId());
        assertEquals("VOUCHER", saved.getMethod());
        assertEquals("SUCCESS", saved.getStatus());
    }

    @Test
    void save_withNullPayment_returnsNull() {
        Payment result = repository.save(null);

        assertNull(result);
    }

    @Test
    void save_withNullId_returnsNull() {
        Payment payment = Payment.builder()
                .id(null)
                .method("VOUCHER")
                .status("SUCCESS")
                .build();

        Payment result = repository.save(payment);

        assertNull(result);
    }

    @Test
    void save_updatesExistingPayment() {
        Payment payment1 = Payment.builder()
                .id("pay-2")
                .method("VOUCHER")
                .status("SUCCESS")
                .build();
        repository.save(payment1);

        Payment payment2 = Payment.builder()
                .id("pay-2")
                .method("BANK_TRANSFER")
                .status("PENDING")
                .build();
        Payment updated = repository.save(payment2);

        assertNotNull(updated);
        assertEquals("pay-2", updated.getId());
        assertEquals("BANK_TRANSFER", updated.getMethod());
        assertEquals("PENDING", updated.getStatus());

        List<Payment> all = repository.findAll();
        assertEquals(1, all.size());
    }

    @Test
    void save_updatesPaymentWithNullIdInRepository() throws Exception {
        // Create a payment with null id using builder
        Payment paymentWithNullId = Payment.builder()
                .id(null)
                .method("VOUCHER")
                .status("SUCCESS")
                .build();
        
        // Use reflection to add to the internal list
        Field field = PaymentRepository.class.getDeclaredField("paymentData");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Payment> paymentData = (List<Payment>) field.get(repository);
        paymentData.add(paymentWithNullId);

        // Now try to update with a valid payment
        Payment newPayment = Payment.builder()
                .id("pay-update")
                .method("BANK_TRANSFER")
                .status("PENDING")
                .build();
        
        Payment result = repository.save(newPayment);
        
        // Should add new payment (not update) because stored.getId() is null
        assertNotNull(result);
        assertEquals("pay-update", result.getId());
        
        // Should have 2 payments now
        List<Payment> all = repository.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void findById_existingId_returnsPayment() {
        Payment payment = Payment.builder()
                .id("pay-3")
                .method("VOUCHER")
                .status("SUCCESS")
                .build();
        repository.save(payment);

        Payment found = repository.findById("pay-3");

        assertNotNull(found);
        assertEquals("pay-3", found.getId());
    }

    @Test
    void findById_nonExistingId_returnsNull() {
        Payment found = repository.findById("non-existing-id");

        assertNull(found);
    }

    @Test
    void findById_nullId_returnsNull() {
        Payment found = repository.findById(null);

        assertNull(found);
    }

    @Test
    void findById_withNullIdInRepository_returnsNull() throws Exception {
        // Create a payment with null id
        Payment paymentWithNullId = Payment.builder()
                .id(null)
                .method("VOUCHER")
                .status("SUCCESS")
                .build();
        
        // Use reflection to add to the internal list
        Field field = PaymentRepository.class.getDeclaredField("paymentData");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Payment> paymentData = (List<Payment>) field.get(repository);
        paymentData.add(paymentWithNullId);

        // Try to find it
        Payment found = repository.findById(null);
        
        // Should return null since null id cannot be matched
        assertNull(found);
    }

    @Test
    void findAll_emptyRepository_returnsEmptyList() {
        List<Payment> result = repository.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_withMultiplePayments_returnsAll() {
        Payment payment1 = Payment.builder()
                .id("pay-4")
                .method("VOUCHER")
                .status("SUCCESS")
                .build();
        Payment payment2 = Payment.builder()
                .id("pay-5")
                .method("BANK_TRANSFER")
                .status("PENDING")
                .build();
        Payment payment3 = Payment.builder()
                .id("pay-6")
                .method("VOUCHER")
                .status("REJECTED")
                .build();

        repository.save(payment1);
        repository.save(payment2);
        repository.save(payment3);

        List<Payment> all = repository.findAll();

        assertEquals(3, all.size());
    }

    @Test
    void findAll_returnsCopyNotOriginalList() {
        Payment payment = Payment.builder()
                .id("pay-7")
                .method("VOUCHER")
                .status("SUCCESS")
                .build();
        repository.save(payment);

        List<Payment> firstCall = repository.findAll();
        List<Payment> secondCall = repository.findAll();

        assertNotSame(firstCall, secondCall);
        assertEquals(firstCall.size(), secondCall.size());
    }
}
