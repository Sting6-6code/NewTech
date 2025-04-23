/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.fintech;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Order.Order;
import Business.Order.OrderDirectory;
import Business.Organization.Organization;
import Business.Payment.Payment;
import Business.Payment.PaymentDirectory;
import Business.UserAccount.UserAccount;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yushe
 */
public class FintechHomepage extends javax.swing.JPanel {
    private JPanel userProcessContainer;
    private UserAccount userAccount;
    private Organization organization;
    private Enterprise enterprise;
    private EcoSystem business;
    private PaymentDirectory paymentDirectory;
    private OrderDirectory orderDirectory;

    /**
     * Creates new form FintechHomepage
     */
    public FintechHomepage() {
        System.out.println("Debug: Default constructor called");
        initComponents();
        orderDirectory = new OrderDirectory();
        paymentDirectory = new PaymentDirectory();
        setupPaymentTable();
    }

    public FintechHomepage(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        System.out.println("Debug: Parameterized constructor called");
        System.out.println("Debug: business is " + (business == null ? "null" : "not null"));
        System.out.println("Debug: account is " + (account == null ? "null" : "not null"));
        System.out.println("Debug: organization is " + (organization == null ? "null" : "not null"));
        System.out.println("Debug: enterprise is " + (enterprise == null ? "null" : "not null"));
        
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.organization = organization;
        this.enterprise = enterprise;
        this.business = business;
        
        // Ensure business is not null
        if (business == null) {
            System.out.println("Error: Business is null");
            return;
        }
        
        // Initialize payment directory
        this.paymentDirectory = business.getPaymentDirectory();
        if (this.paymentDirectory == null) {
            System.out.println("Warning: Payment directory is null, creating new one");
            this.paymentDirectory = new PaymentDirectory();
            business.setPaymentDirectory(this.paymentDirectory);
        }
        
        // Initialize order directory
        this.orderDirectory = business.getOrderDirectory();
        if (this.orderDirectory == null) {
            System.out.println("Warning: Order directory is null, creating new one");
            this.orderDirectory = new OrderDirectory();
            business.setOrderDirectory(this.orderDirectory);
        }
        
        System.out.println("Debug: After initialization");
        System.out.println("Debug: orderDirectory is " + (this.orderDirectory == null ? "null" : "not null"));
        System.out.println("Debug: paymentDirectory is " + (this.paymentDirectory == null ? "null" : "not null"));
        
        setupPaymentTable();
        populatePaymentTable();
        calculateTotalRevenue();
        calculateDailyRevenue();
        updatePaymentStatusSummary();
    }

    private void setupPaymentTable() {
        System.out.println("Debug: Setting up payment table");
        DefaultTableModel model = (DefaultTableModel) tblPayments.getModel();
        model.setRowCount(0);
        String[] columnNames = {"Payment ID", "Order ID", "Amount", "Status", "Date"};
        model.setColumnIdentifiers(columnNames);
    }

    private void populatePaymentTable() {
        System.out.println("Debug: Starting populatePaymentTable");
        DefaultTableModel model = (DefaultTableModel) tblPayments.getModel();
        model.setRowCount(0);
        
        System.out.println("Debug: paymentDirectory is " + (paymentDirectory == null ? "null" : "not null"));
        
        // Get all payments from the payment directory
        for (Payment payment : paymentDirectory.getPaymentList()) {
            Object[] row = new Object[5];
            row[0] = payment.getPaymentId();
            row[1] = payment.getOrder().getOrderId();
            row[2] = String.format("$%.2f", payment.getAmount());
            row[3] = payment.getStatus();
            row[4] = new SimpleDateFormat("MM/dd/yyyy").format(payment.getPaymentDate());
            model.addRow(row);
        }
        
        System.out.println("Debug: Finished populatePaymentTable");
    }

    private void calculateTotalRevenue() {
        double total = 0.0;
        for (Payment payment : paymentDirectory.getPaymentList()) {
            if (payment.getStatus().equals("Completed")) {
                total += payment.getAmount();
            }
        }
        lblTotalRevenue.setText(String.format("Total Revenue: $%.2f", total));
    }

    private void calculateDailyRevenue() {
        double dailyTotal = 0.0;
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String todayStr = dateFormat.format(today);
        
        for (Payment payment : paymentDirectory.getPaymentList()) {
            String paymentDate = dateFormat.format(payment.getPaymentDate());
            if (paymentDate.equals(todayStr) && payment.getStatus().equals("Completed")) {
                dailyTotal += payment.getAmount();
            }
        }
        lblDailyRevenue.setText(String.format("Today's Revenue: $%.2f", dailyTotal));
    }

    private void updatePaymentStatusSummary() {
        int completed = 0;
        int pending = 0;
        int refunded = 0;
        
        for (Payment payment : paymentDirectory.getPaymentList()) {
            switch (payment.getStatus()) {
                case "Completed":
                    completed++;
                    break;
                case "Pending":
                    pending++;
                    break;
                case "Refunded":
                    refunded++;
                    break;
            }
        }
        
        lblStatusSummary.setText(String.format("Payments: %d Completed, %d Pending, %d Refunded", 
            completed, pending, refunded));
    }

    private void processPayment() {
        String orderId = txtShipmentID.getText();
        String amountStr = txtAmt.getText();
        
        if (orderId.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Order ID and Amount");
            return;
        }
        
        try {
            double amount = Double.parseDouble(amountStr);
            Order order = orderDirectory.findOrderByRequestId(orderId);
            
            if (order == null) {
                JOptionPane.showMessageDialog(this, "Order not found");
                return;
            }
            
            Payment payment = new Payment(order);
            payment.setAmount(amount);
            payment.setStatus("Completed");
            paymentDirectory.getPaymentList().add(payment);
            
            JOptionPane.showMessageDialog(this, "Payment processed successfully");
            populatePaymentTable();
            calculateTotalRevenue();
            calculateDailyRevenue();
            updatePaymentStatusSummary();
            
            // Clear input fields
            txtShipmentID.setText("");
            txtAmt.setText("");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        viewPaymentsScrollPane = new javax.swing.JScrollPane();
        tblPayments = new javax.swing.JTable();
        btnReceive = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        btnRefund = new javax.swing.JButton();
        btnViewDetail = new javax.swing.JButton();
        viewDetailJP = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtShipmentID = new javax.swing.JTextField();
        txtAmt = new javax.swing.JTextField();
        lblTotalRevenue = new javax.swing.JLabel();
        lblDailyRevenue = new javax.swing.JLabel();
        lblStatusSummary = new javax.swing.JLabel();

        lblTitle.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitle.setText("Welcome to Payment Center!");

        tblPayments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Payment ID", "Shipment Info", "Amount"
            }
        ));
        viewPaymentsScrollPane.setViewportView(tblPayments);

        btnReceive.setText("Receive");

        btnReport.setText("Report Suspicious");

        btnRefund.setText("Refund");

        btnViewDetail.setText("View Detail..");

        viewDetailJP.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Shipment ID:");

        jLabel2.setText("Amount:");

        lblTotalRevenue.setText("Total Revenue: $0.00");
        lblDailyRevenue.setText("Today's Revenue: $0.00");
        lblStatusSummary.setText("Payments: 0 Completed, 0 Pending, 0 Refunded");

        javax.swing.GroupLayout viewDetailJPLayout = new javax.swing.GroupLayout(viewDetailJP);
        viewDetailJP.setLayout(viewDetailJPLayout);
        viewDetailJPLayout.setHorizontalGroup(
            viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewDetailJPLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(39, 39, 39)
                .addGroup(viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAmt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtShipmentID, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(182, Short.MAX_VALUE))
        );

        viewDetailJPLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtAmt, txtShipmentID});

        viewDetailJPLayout.setVerticalGroup(
            viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewDetailJPLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtShipmentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtAmt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(357, 357, 357)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(viewPaymentsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(btnReceive)
                        .addGap(87, 87, 87)
                        .addComponent(btnReport)
                        .addGap(85, 85, 85)
                        .addComponent(btnRefund)
                        .addGap(67, 67, 67)
                        .addComponent(btnViewDetail))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(viewDetailJP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lblTitle)
                .addGap(86, 86, 86)
                .addComponent(viewPaymentsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReceive)
                    .addComponent(btnReport)
                    .addComponent(btnRefund)
                    .addComponent(btnViewDetail))
                .addGap(70, 70, 70)
                .addComponent(viewDetailJP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReceive;
    private javax.swing.JButton btnRefund;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnViewDetail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblPayments;
    private javax.swing.JTextField txtAmt;
    private javax.swing.JTextField txtShipmentID;
    private javax.swing.JPanel viewDetailJP;
    private javax.swing.JScrollPane viewPaymentsScrollPane;
    private javax.swing.JLabel lblTotalRevenue;
    private javax.swing.JLabel lblDailyRevenue;
    private javax.swing.JLabel lblStatusSummary;
    // End of variables declaration//GEN-END:variables
}
