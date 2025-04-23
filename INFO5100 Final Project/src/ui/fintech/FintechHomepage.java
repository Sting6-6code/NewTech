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
    private Payment selectedPayment;

    /**
     * Creates new form FintechHomepage
     */
    public FintechHomepage() {
        initComponents();
        orderDirectory = new OrderDirectory();
        paymentDirectory = new PaymentDirectory();
        setupPaymentTable();
        setupTableSelectionListener();
        setViewDetailPanelEnabled(false);
    }

    public FintechHomepage(JPanel userProcessContainer, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem business) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.userAccount = account;
        this.organization = organization;
        this.enterprise = enterprise;
        this.business = business;
        
        if (business == null) {
            return;
        }
        
        this.paymentDirectory = business.getPaymentDirectory();
        if (this.paymentDirectory == null) {
            this.paymentDirectory = new PaymentDirectory();
            business.setPaymentDirectory(this.paymentDirectory);
        }
        
        this.orderDirectory = business.getOrderDirectory();
        if (this.orderDirectory == null) {
            this.orderDirectory = new OrderDirectory();
            business.setOrderDirectory(this.orderDirectory);
        }
        
        createSamplePayments();
        setupPaymentTable();
        populatePaymentTable();
        setupTableSelectionListener();
        setViewDetailPanelEnabled(false);
    }

    private void setupTableSelectionListener() {
        tblPayments.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblPayments.getSelectedRow();
                if (selectedRow >= 0) {
                    String paymentId = (String) tblPayments.getValueAt(selectedRow, 0);
                    selectedPayment = paymentDirectory.findPaymentById(paymentId);
                    updateButtonStates();
                } else {
                    selectedPayment = null;
                    updateButtonStates();
                }
            }
        });
    }

    private void updateButtonStates() {
        if (selectedPayment != null) {
            String status = selectedPayment.getStatus();
            boolean isRefunded = status.equals("Refunded");
            boolean isUnderReview = status.equals("Under Review");
            boolean isDenied = status.equals("Denied");
            boolean isReportable = status.equals("Pending") || status.equals("Completed");
            
            btnRefund.setEnabled(isDenied && !isRefunded);
            btnViewDetail.setEnabled(!isRefunded);
            btnReport.setEnabled(isReportable && !isRefunded && !isUnderReview);
            
            if (isRefunded) {
                btnRefund.setToolTipText("This payment has already been refunded");
                btnViewDetail.setToolTipText("This payment has been refunded");
                btnReport.setToolTipText("This payment has been refunded");
            } else if (isUnderReview) {
                btnRefund.setToolTipText("This payment is under review and cannot be refunded");
                btnReport.setToolTipText("This payment is already under review");
            } else if (!isDenied) {
                btnRefund.setToolTipText("Only denied payments can be refunded");
            } else if (!isReportable) {
                btnReport.setToolTipText("Only pending or completed payments can be reported as suspicious");
            } else {
                btnRefund.setToolTipText(null);
                btnViewDetail.setToolTipText(null);
                btnReport.setToolTipText(null);
            }
        } else {
            btnRefund.setEnabled(false);
            btnViewDetail.setEnabled(false);
            btnReport.setEnabled(false);
        }
    }

    private void setViewDetailPanelEnabled(boolean enabled) {
        viewDetailJP.setEnabled(enabled);
        txtPaymentID.setEnabled(enabled);
        txtSingleItemPrice.setEnabled(enabled);
        txtAmount.setEnabled(enabled);
        txtSubTotal.setEnabled(enabled);
        
        if (!enabled) {
            clearViewDetailPanel();
        }
    }

    private void clearViewDetailPanel() {
        txtPaymentID.setText("");
        txtSingleItemPrice.setText("");
        txtAmount.setText("");
        txtSubTotal.setText("");
    }

    private void populateViewDetailPanel(Payment payment) {
        txtPaymentID.setText(payment.getPaymentId());
        txtSingleItemPrice.setText(String.format("$%.2f", payment.getSingleItemPrice()));
        txtAmount.setText(String.format("%d", payment.getOrderAmount()));
        txtSubTotal.setText(String.format("$%.2f", payment.getAmount()));
    }

    private void setupPaymentTable() {
        DefaultTableModel model = (DefaultTableModel) tblPayments.getModel();
        model.setRowCount(0);
        String[] columnNames = {"Payment ID", "Order ID", "Amount", "Status", "Date"};
        model.setColumnIdentifiers(columnNames);
    }

    private void populatePaymentTable() {
        DefaultTableModel model = (DefaultTableModel) tblPayments.getModel();
        model.setRowCount(0);
        
        for (Payment payment : paymentDirectory.getPaymentList()) {
            Object[] row = new Object[5];
            row[0] = payment.getPaymentId();
            row[1] = payment.getOrder().getOrderId();
            row[2] = String.format("$%.2f", payment.getAmount());
            row[3] = payment.getStatus();
            row[4] = new SimpleDateFormat("MM/dd/yyyy").format(payment.getPaymentDate());
            model.addRow(row);
        }
    }

    private void processPayment() {
        String orderId = txtPaymentID.getText();
        String amountStr = txtSingleItemPrice.getText();
        
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
            
            txtPaymentID.setText("");
            txtSingleItemPrice.setText("");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount");
        }
    }

    private void createSamplePayments() {
        // Only create sample payments if the directory is empty
        if (paymentDirectory.getPaymentList().isEmpty()) {
            // Create new sample payments
            for (int i = 0; i < 30; i++) {
                Order order = new Order();
                int itemCount = (int) (Math.random() * 30) + 1;
                double itemPrice = 500 + (Math.random() * 1500);
                double totalAmount = itemCount * itemPrice;
                
                order.setTotalAmount(totalAmount);
                order.setOrderId("ORD" + String.format("%04d", i + 1));
                
                Payment payment = new Payment(order);
                payment.setSingleItemPrice(itemPrice);
                payment.setOrderAmount(itemCount);
                // 30% chance of being Completed, 70% chance of being Pending
                payment.setStatus(Math.random() < 0.3 ? "Completed" : "Pending");
                payment.setPaymentDate(new Date(System.currentTimeMillis() - (long)(Math.random() * 30 * 24 * 60 * 60 * 1000)));
                
                paymentDirectory.getPaymentList().add(payment);
            }
        }
    }

    private void btnViewDetailActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedPayment != null) {
            setViewDetailPanelEnabled(true);
            populateViewDetailPanel(selectedPayment);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a payment from the table to view details.", 
                "No Payment Selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedPayment != null) {
            selectedPayment.setStatus("Under Review");
            paymentDirectory.addSuspiciousPayment(selectedPayment);
            JOptionPane.showMessageDialog(this, 
                "This payment has been flagged and is now under review.",
                "Payment Flagged", 
                JOptionPane.INFORMATION_MESSAGE);
            populatePaymentTable(); // Refresh the table to show updated status
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please select a payment from the table to report as suspicious.",
                "No Payment Selected", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void btnRevActionPerformed(java.awt.event.ActionEvent evt) {
        double totalRevenue = 0.0;
        
        // Calculate revenue from allowed payments
        for (Payment payment : business.getPaymentDirectory().getPaymentList()) {
            if (payment.getStatus().equals("Allowed") || payment.getStatus().equals("Completed")) {
                totalRevenue += payment.getAmount();
            }
        }
        
        // Display the revenue in a message dialog
        JOptionPane.showMessageDialog(this, 
            String.format("The revenue is $%.2f", totalRevenue),
            "Revenue Summary",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnRefundActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedPayment != null) {
            // Confirm refund
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to refund this payment?",
                "Confirm Refund",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Update payment status
                selectedPayment.setStatus("Refunded");
                
                // Refresh the table and update button states
                populatePaymentTable();
                updateButtonStates();
                
                JOptionPane.showMessageDialog(this,
                    "Payment has been refunded successfully",
                    "Refund Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Please select a payment to refund",
                "No Payment Selected",
                JOptionPane.WARNING_MESSAGE);
        }
    }

//    public void dispose() {
//        // Clear all payments when the application exits
//        if (paymentDirectory != null) {
//            paymentDirectory.removeAll();
//        }
//        super.dispose();
//    }

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
        btnRefund = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        btnRev = new javax.swing.JButton();
        btnViewDetail = new javax.swing.JButton();
        viewDetailJP = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPaymentID = new javax.swing.JTextField();
        txtSingleItemPrice = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();

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

        btnRefund.setText("Refund");
        btnRefund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefundActionPerformed(evt);
            }
        });

        btnReport.setText("Report Suspicious");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        btnRev.setText("View Revenue");
        btnRev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevActionPerformed(evt);
            }
        });

        btnViewDetail.setText("View Detail..");

        viewDetailJP.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Payment ID:");

        jLabel2.setText("Single Item Price:");

        jLabel3.setText("Amount:");

        jLabel4.setText("Subtotal:");

        javax.swing.GroupLayout viewDetailJPLayout = new javax.swing.GroupLayout(viewDetailJP);
        viewDetailJP.setLayout(viewDetailJPLayout);
        viewDetailJPLayout.setHorizontalGroup(
            viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewDetailJPLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addGroup(viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(39, 39, 39)
                .addGroup(viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSingleItemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(182, Short.MAX_VALUE))
        );

        viewDetailJPLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtAmount, txtPaymentID, txtSingleItemPrice, txtSubTotal});

        viewDetailJPLayout.setVerticalGroup(
            viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewDetailJPLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSingleItemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(viewDetailJPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
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
                        .addComponent(btnRefund)
                        .addGap(73, 73, 73)
                        .addComponent(btnReport)
                        .addGap(75, 75, 75)
                        .addComponent(btnViewDetail)
                        .addGap(52, 52, 52)
                        .addComponent(btnRev))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(266, 266, 266)
                        .addComponent(viewDetailJP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(117, Short.MAX_VALUE))
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
                    .addComponent(btnRefund)
                    .addComponent(btnReport)
                    .addComponent(btnViewDetail)
                    .addComponent(btnRev))
                .addGap(70, 70, 70)
                .addComponent(viewDetailJP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefund;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnRev;
    private javax.swing.JButton btnViewDetail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblPayments;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtPaymentID;
    private javax.swing.JTextField txtSingleItemPrice;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JPanel viewDetailJP;
    private javax.swing.JScrollPane viewPaymentsScrollPane;
    // End of variables declaration//GEN-END:variables
}
