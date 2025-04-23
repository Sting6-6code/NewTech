package Business.Payment;

import Business.Order.Order;
import java.util.Date;

public class Payment {
    private String paymentId;
    private Order order;
    private double amount;
    private String status;
    private Date paymentDate;
    private static int counter = 0;

    public Payment() {
        paymentId = "PAY" + String.format("%04d", ++counter);
        paymentDate = new Date();
    }

    public Payment(Order order) {
        this();
        this.order = order;
        this.amount = order.getTotalAmount();
        this.status = "Pending";
    }

    public String getPaymentId() {
        return paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return paymentId;
    }
} 