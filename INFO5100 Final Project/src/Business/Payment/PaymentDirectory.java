package Business.Payment;

import Business.Order.Order;
import java.util.ArrayList;

public class PaymentDirectory {
    private ArrayList<Payment> paymentList;
    private ArrayList<Payment> suspiciousPayments;

    public PaymentDirectory() {
        paymentList = new ArrayList<>();
        suspiciousPayments = new ArrayList<>();
    }

    public ArrayList<Payment> getPaymentList() {
        return paymentList;
    }

    public ArrayList<Payment> getSuspiciousPayments() {
        return suspiciousPayments;
    }

    public Payment createPayment(Order order) {
        Payment payment = new Payment(order);
        paymentList.add(payment);
        return payment;
    }

    public void removePayment(Payment payment) {
        paymentList.remove(payment);
    }
    
    public void removeAll() {
        paymentList = new ArrayList<>();
        suspiciousPayments = new ArrayList<>();
    }
    
    public Payment findPaymentById(String paymentId) {
        for (Payment payment : paymentList) {
            if (payment.getPaymentId().equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }

    public void addSuspiciousPayment(Payment payment) {
        if (payment != null && !suspiciousPayments.contains(payment)) {
            suspiciousPayments.add(payment);
        }
    }

    public void removeSuspiciousPayment(Payment payment) {
        suspiciousPayments.remove(payment);
    }
} 