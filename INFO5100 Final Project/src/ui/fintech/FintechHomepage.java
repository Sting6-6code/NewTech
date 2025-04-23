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
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yushe
 */
public class FintechHomepage extends javax.swing.JPanel {
    private EcoSystem business;
    private UserAccount userAccount;
    private Organization organization;
    private Enterprise enterprise;
    private OrderDirectory orderDirectory;
    private PaymentDirectory paymentDirectory;

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
        this.business = business;
        this.userAccount = account;
        this.organization = organization;
        this.enterprise = enterprise;
        this.orderDirectory = business.getOrderDirectory();
        this.paymentDirectory = business.getPaymentDirectory();
        
        System.out.println("Debug: After initialization");
        System.out.println("Debug: orderDirectory is " + (this.orderDirectory == null ? "null" : "not null"));
        System.out.println("Debug: paymentDirectory is " + (this.paymentDirectory == null ? "null" : "not null"));
        
        setupPaymentTable();
        populatePaymentTable();
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
        
        System.out.println("Debug: business is " + (business == null ? "null" : "not null"));
        
        // Get all orders and create payments for them
        OrderDirectory orderDir = business.getOrderDirectory();
        PaymentDirectory paymentDir = business.getPaymentDirectory();
        
        System.out.println("Debug: orderDir is " + (orderDir == null ? "null" : "not null"));
        System.out.println("Debug: paymentDir is " + (paymentDir == null ? "null" : "not null"));
        
        if (orderDir != null && orderDir.getOrderList() != null) {
            System.out.println("Debug: Number of orders found: " + orderDir.getOrderList().size());
            
            // Create payments for all orders if they don't exist
            for (Order order : orderDir.getOrderList()) {
                System.out.println("Debug: Processing order: " + order.getOrderId());
                if (order != null) {
                    // Check if payment already exists for this order
                    boolean paymentExists = false;
                    for (Payment payment : paymentDir.getPaymentList()) {
                        if (payment.getOrder().equals(order)) {
                            paymentExists = true;
                            break;
                        }
                    }
                    
                    if (!paymentExists) {
                        paymentDir.createPayment(order);
                        System.out.println("Debug: Created new payment for order: " + order.getOrderId());
                    }
                }
            }
            
            // Display all payments
            for (Payment payment : paymentDir.getPaymentList()) {
                System.out.println("Debug: Processing payment: " + payment.getPaymentId());
                if (payment != null && payment.getOrder() != null) {
                    Object[] row = new Object[5];
                    row[0] = payment.getPaymentId();
                    row[1] = payment.getOrder().getOrderId();
                    row[2] = String.format("$%.2f", payment.getAmount());
                    row[3] = payment.getStatus();
                    row[4] = new SimpleDateFormat("MM/dd/yyyy").format(payment.getPaymentDate());
                    model.addRow(row);
                    System.out.println("Debug: Added row for payment: " + payment.getPaymentId());
                } else {
                    System.out.println("Debug: Found null payment or order in list");
                }
            }
        } else {
            System.out.println("Debug: orderDir or orderList is null");
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
    // End of variables declaration//GEN-END:variables
}
