package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        if (order == null || order.getId() == null) return null;

        String id = UUID.randomUUID().toString();
        String status = "PENDING";

        if (method != null && method.equalsIgnoreCase("VOUCHER")) {
            String code = paymentData == null ? null : paymentData.get("voucherCode");
            if (isValidVoucher(code)) {
                status = "SUCCESS";
            } else {
                status = "REJECTED";
            }
        } else if (method != null && method.equalsIgnoreCase("BANK_TRANSFER")) {
            String bank = paymentData == null ? null : paymentData.get("bankName");
            String ref = paymentData == null ? null : paymentData.get("referenceCode");
            if (bank == null || bank.isEmpty() || ref == null || ref.isEmpty()) {
                status = "REJECTED";
            } else {
                status = "SUCCESS";
            }
        }

        Payment p = new Payment(id, method, status, paymentData, order);
        paymentRepository.save(p);

        if ("SUCCESS".equals(status)) {
            order.setStatus(OrderStatus.SUCCESS.getValue());
            orderRepository.save(order);
        } else if ("REJECTED".equals(status)) {
            order.setStatus(OrderStatus.FAILED.getValue());
            orderRepository.save(order);
        }

        return p;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (payment == null || status == null) return null;
        payment.setStatus(status);
        paymentRepository.save(payment);

        Order order = payment.getOrder();
        if (order != null) {
            if ("SUCCESS".equals(status)) {
                order.setStatus(OrderStatus.SUCCESS.getValue());
                orderRepository.save(order);
            } else if ("REJECTED".equals(status)) {
                order.setStatus(OrderStatus.FAILED.getValue());
                orderRepository.save(order);
            }
        }
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {return null;}

    @Override
    public List<Payment> getAllPayments() {return null;}

    private boolean isValidVoucher(String code) {return false;}
}
