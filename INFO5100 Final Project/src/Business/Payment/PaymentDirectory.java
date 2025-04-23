package Business.Payment;

import Business.Order.Order;
import java.util.ArrayList;

public class PaymentDirectory {
    private ArrayList<Payment> paymentList;

    public PaymentDirectory() {
        paymentList = new ArrayList<>();
    }

    public ArrayList<Payment> getPaymentList() {
        return paymentList;
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
    }
} 