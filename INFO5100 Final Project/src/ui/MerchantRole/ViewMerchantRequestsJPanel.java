/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.MerchantRole;

import ui.ProcurementSpecialistRole.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.CardLayout;
import Business.Order.Order;
import Business.Order.OrderDirectory;
import Business.Warehouse.Warehouse;
import Business.WorkQueue.MerchantWorkRequest;
import Business.WorkQueue.WorkRequest;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import java.awt.Component;

/**
 *
 * @author wangsiting
 */
public class ViewMerchantRequestsJPanel extends javax.swing.JPanel {

    private OrderDirectory orderDirectory;
    private DefaultTableModel cartTableModel;
    private JPanel userProcessContainer;
    private Warehouse warehouse;
    private javax.swing.JTable tblCart;

    /**
     * Creates new form WarehouseRequestsJPanel
     */
    public ViewMerchantRequestsJPanel(JPanel userProcessContainer) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        orderDirectory = new OrderDirectory();
        //warehouse = Warehouse.getInstance();
        setupListeners();
        
        // Load all merchant purchase requests
        loadMerchantRequests();
        
        // Apply UI theme
        setupTheme();
    }

    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set panel background color
        this.setBackground(new Color(240, 245, 255));
        jPanel1.setBackground(new Color(240, 245, 255));
        
        // Style buttons
        styleButton(btnSearch);
        styleButton(btnRefresh);
        styleButton(btnBack);
        
        // Style text fields
        styleTextField(txtSearchRequestID);
        
        // Style combo box
        styleComboBox(StatusjComboBox);
        
        // Style labels
        styleTitleLabel(lblTitle);
        styleLabel(jLabel1);
        
        // Style table
        styleTable(RequestTable1);
    }
    
    /**
     * Apply consistent styling to a button
     * @param button Button to style
     */
    private void styleButton(JButton button) {
        button.setBackground(new Color(26, 79, 156)); // Medium blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        // Add a subtle border with rounded corners
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(13, 60, 130), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
    
    /**
     * Apply consistent styling to a text field
     * @param textField TextField to style
     */
    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(245, 245, 250)); // Light gray-white background
        textField.setForeground(new Color(13, 25, 51)); // Dark blue text
        textField.setCaretColor(new Color(26, 79, 156)); // Medium blue cursor
        textField.setBorder(BorderFactory.createLineBorder(new Color(90, 141, 224), 1));
        textField.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.PLAIN, 14));
    }
    
    /**
     * Style a combo box to match the theme
     * @param comboBox ComboBox to style
     */
    private void styleComboBox(JComboBox comboBox) {
        comboBox.setBackground(new Color(245, 245, 250)); // Light gray-white background
        comboBox.setForeground(new Color(13, 25, 51)); // Dark blue text
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(90, 141, 224), 1));
        comboBox.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.PLAIN, 14));
        
        // Style the UI if possible
        try {
            comboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
                @Override
                protected JButton createArrowButton() {
                    JButton button = super.createArrowButton();
                    button.setBackground(new Color(26, 79, 156));
                    button.setBorder(BorderFactory.createLineBorder(new Color(90, 141, 224)));
                    return button;
                }
            });
        } catch (Exception e) {
            System.out.println("Could not fully style combo box: " + e.getMessage());
        }
    }
    
    /**
     * Apply title label styling
     * @param label Label to style
     */
    private void styleTitleLabel(JLabel label) {
        label.setForeground(new Color(26, 79, 156));
        label.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.BOLD, 20));
    }
    
    /**
     * Apply regular label styling
     * @param label Label to style
     */
    private void styleLabel(JLabel label) {
        label.setForeground(new Color(26, 79, 156));
        label.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.PLAIN, 14));
    }
    
    /**
     * Style table with consistent theme
     * @param table Table to style
     */
    private void styleTable(JTable table) {
        // Style table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(26, 79, 156));
        header.setForeground(Color.WHITE);
        header.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.BOLD, 14));
        header.setBorder(new LineBorder(new Color(13, 60, 130)));
        
        // Style table - using darker colors for better visibility
        table.setBackground(new Color(240, 240, 250)); // Slightly darker background
        table.setForeground(new Color(0, 0, 0)); // Black text for maximum contrast
        table.setGridColor(new Color(180, 195, 235)); // Darker grid lines
        table.setSelectionBackground(new Color(90, 141, 224));
        table.setSelectionForeground(Color.WHITE);
        table.setFont(new java.awt.Font("Helvetica Neue", java.awt.Font.BOLD, 14)); // Bold font for better visibility
        table.setRowHeight(30); // Slightly increase row height for better readability
        
        // Set alternating row colors with more contrast
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(240, 240, 250) : new Color(220, 220, 235));
                    c.setForeground(new Color(0, 0, 0)); // Ensure text is always black for maximum contrast
                }
                return c;
            }
        });
        
        // Style scroll pane
        jScrollPane1.setBorder(new LineBorder(new Color(26, 79, 156)));
    }

    private void setupCartTable() {
        String[] columnNames = {"Product Name", "Purchase Cost", "Quantity", "Total Amount"};
        if (tblCart == null) {
            tblCart = new javax.swing.JTable();
            cartTableModel = new DefaultTableModel(columnNames, 0);
            tblCart.setModel(cartTableModel);
        } else {
            cartTableModel = (DefaultTableModel) tblCart.getModel();
            cartTableModel.setColumnIdentifiers(columnNames);
        }
    }
    
    private void loadMerchantRequests() {
        // Clear the table
        DefaultTableModel model = (DefaultTableModel) RequestTable1.getModel();
        model.setRowCount(0);
        
        // Print debug information
        System.out.println("Starting to load merchant request table...");
        
        // Get merchant requests from EcoSystem
        List<WorkRequest> requests = new ArrayList<>();
        Business.EcoSystem system = Business.EcoSystem.getInstance();
        if (system.getWorkQueue() != null) {
            System.out.println("System work queue exists, total requests: " + system.getWorkQueue().getWorkRequestList().size());
            for (WorkRequest req : system.getWorkQueue().getWorkRequestList()) {
                if (req instanceof MerchantWorkRequest) {
                    MerchantWorkRequest merchantReq = (MerchantWorkRequest) req;
                    // Ensure request has at least a product ID before adding
                    if (merchantReq.getProductId() != null && !merchantReq.getProductId().trim().isEmpty()) {
                        requests.add(req);
                        System.out.println("Found valid merchant request: Product=" + merchantReq.getProductName() + 
                                         ", ID=" + merchantReq.getProductId() + 
                                         ", Status=" + merchantReq.getStatus());
                    } else {
                        System.out.println("Skipping invalid request: Product ID is empty");
                    }
                }
            }
        } else {
            System.err.println("Error: System work queue is empty");
        }
        
        if (requests.isEmpty()) {
            System.out.println("No valid merchant requests found, table will be empty!");
            return;
        }
        
        System.out.println("Found " + requests.size() + " valid merchant requests");
        
        // Filter requests (based on dropdown status)
        String selectedStatus = StatusjComboBox.getSelectedItem().toString();
        String searchId = txtSearchRequestID.getText().trim();
        
        System.out.println("Filter criteria - Status: " + selectedStatus + ", Search text: " + searchId);
        
        // If search box contains default hint text, treat as empty search
        if ("Saerch Request ID...".equals(searchId)) {
            searchId = "";
        }
        
        int addedRows = 0;
        for (WorkRequest request : requests) {
            MerchantWorkRequest merchantRequest = (MerchantWorkRequest) request;
            
            // Create request ID: REQ-ProductID
            String requestId = "REQ-" + merchantRequest.getProductId();
            
            // Filter by status
            boolean statusMatch = "All".equals(selectedStatus) || selectedStatus.isEmpty() || 
                           merchantRequest.getStatus() == null || merchantRequest.getStatus().equals(selectedStatus);
            
            // Filter by ID or product name
            boolean searchMatch = searchId.isEmpty() || 
                           requestId.toLowerCase().contains(searchId.toLowerCase()) || 
                           (merchantRequest.getProductName() != null && 
                            merchantRequest.getProductName().toLowerCase().contains(searchId.toLowerCase()));
            
            System.out.println("Checking request " + requestId + " - Status match: " + statusMatch + ", Search match: " + searchMatch);
            
            if (statusMatch && searchMatch) {
                // Prevent errors from null values
                String displayProductName = merchantRequest.getProductName() != null ? 
                                          merchantRequest.getProductName() : "Unnamed Product";
                String displayStatus = merchantRequest.getStatus() != null ? 
                                     merchantRequest.getStatus() : "Pending";
                
                // Create table row
                Object[] row = new Object[5];
                // Table columns: Request ID, Product Name, Quantity, Update Date, Status
                row[0] = requestId;                        // Request ID
                row[1] = displayProductName;               // Product Name
                row[2] = merchantRequest.getRequestedAmount(); // Requested Amount
                row[3] = merchantRequest.getRequestDate(); // Update Date
                row[4] = displayStatus;                    // Status
                
                model.addRow(row);
                addedRows++;
                
                System.out.println("Added to table: " + displayProductName + 
                                 ", Status: " + displayStatus +
                                 ", Requested amount: " + merchantRequest.getRequestedAmount());
            }
        }
        
        System.out.println("Table loading complete, added " + addedRows + " rows of data");
    }
    
    private void refreshRequestTable() {
        loadMerchantRequests();
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        StatusjComboBox = new javax.swing.JComboBox<>();
        txtSearchRequestID = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        RequestTable1 = new javax.swing.JTable();

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("View Merchant Requests");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel1.setText("Status：");

        StatusjComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Pending", "Finished", "Processing", "Rejected", " ", " " }));

        txtSearchRequestID.setText("Saerch Request ID...");
        txtSearchRequestID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchRequestIDActionPerformed(evt);
            }
        });

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh List");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnRefresh)
                .addGap(107, 107, 107)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187)
                .addComponent(txtSearchRequestID, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(StatusjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchRequestID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnRefresh))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        RequestTable1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        RequestTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Merchant Request ID", "Product Name", "Quantity", "Update Date", "Status"
            }
        ));
        jScrollPane1.setViewportView(RequestTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBack)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed

        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        loadMerchantRequests();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchRequestIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchRequestIDActionPerformed
        loadMerchantRequests();
    }//GEN-LAST:event_txtSearchRequestIDActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadMerchantRequests();
    }//GEN-LAST:event_btnRefreshActionPerformed

    

    

    private void updateRequestStatus(String requestId, String newStatus) {
        // 从EcoSystem获取工作队列
        Business.EcoSystem system = Business.EcoSystem.getInstance();
        List<WorkRequest> requests = new ArrayList<>();
        if (system.getWorkQueue() != null) {
            for (WorkRequest req : system.getWorkQueue().getWorkRequestList()) {
                if (req instanceof MerchantWorkRequest) {
                    requests.add(req);
                }
            }
        }
        
        for (WorkRequest request : requests) {
            MerchantWorkRequest merchantRequest = (MerchantWorkRequest) request;
            if (merchantRequest.getMessage().contains(requestId)) {
                merchantRequest.setStatus(newStatus);
                break;
            }
        }
        
        loadMerchantRequests(); // 刷新表格
    }

    private void notifyWarehouse(Order order) {
        String requestId = order.getRequestId();
        int actualAmount = order.getQuantity();
        
        warehouse.processProcurementRequest(requestId, actualAmount);
        
        System.out.println("Processed procurement request: " + requestId + 
                           " with actual amount: " + actualAmount);
    }

    private void StatusjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("状态过滤变更为: " + StatusjComboBox.getSelectedItem().toString());
        loadMerchantRequests();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable RequestTable1;
    private javax.swing.JComboBox<String> StatusjComboBox;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtSearchRequestID;
    // End of variables declaration//GEN-END:variables

    
    // Setup code after constructor to ensure component listeners are correctly registered
    private void setupListeners() {
        System.out.println("Setting up listeners...");
        
        // Add action listener for status dropdown
        // First remove all existing listeners to avoid duplicate registration
        for (java.awt.event.ActionListener al : StatusjComboBox.getActionListeners()) {
            StatusjComboBox.removeActionListener(al);
        }
        
        StatusjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("Status dropdown selection changed: " + StatusjComboBox.getSelectedItem());
                StatusjComboBoxActionPerformed(evt);
            }
        });
        
        System.out.println("Status dropdown listener added");
        
        // Add mouse click listener to request table for displaying details
        RequestTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double click
                    int selectedRow = RequestTable1.getSelectedRow();
                    if (selectedRow >= 0) {
                        System.out.println("Table row double-clicked: " + selectedRow);
                        displayRequestDetails(selectedRow);
                    }
                }
            }
        });
        
        System.out.println("Table mouse listener added");
        
        // Add extra debug output for search button
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("Search button clicked, search content: " + txtSearchRequestID.getText());
                loadMerchantRequests();
            }
        });
        
        // Add extra debug output for refresh button
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("Refresh button clicked");
                loadMerchantRequests();
            }
        });
    }
    
    // Display request details
    private void displayRequestDetails(int selectedRow) {
        try {
            String requestId = RequestTable1.getValueAt(selectedRow, 0).toString();
            String productName = RequestTable1.getValueAt(selectedRow, 1).toString();
            int quantity = Integer.parseInt(RequestTable1.getValueAt(selectedRow, 2).toString());
            String status = RequestTable1.getValueAt(selectedRow, 4).toString();
            
            StringBuilder details = new StringBuilder();
            details.append("Request Details:\n\n");
            details.append("Request ID: ").append(requestId).append("\n");
            details.append("Product Name: ").append(productName).append("\n");
            details.append("Requested Quantity: ").append(quantity).append("\n");
            details.append("Status: ").append(status).append("\n");
            
            // Find request object to get more information
            MerchantWorkRequest foundRequest = findRequestById(requestId);
            if (foundRequest != null) {
                details.append("Price: $").append(foundRequest.getPrice()).append("\n");
                details.append("Total Amount: $").append(foundRequest.getPrice() * foundRequest.getRequestedAmount()).append("\n");
                
                if (foundRequest.getSender() != null) {
                    details.append("Requester: ").append(foundRequest.getSender().getUsername()).append("\n");
                }
                
                if (foundRequest.getReceiver() != null) {
                    details.append("Processor: ").append(foundRequest.getReceiver().getUsername()).append("\n");
                }
                
                if (foundRequest.getResolveDate() != null) {
                    details.append("Completion Date: ").append(foundRequest.getResolveDate()).append("\n");
                }
            }
            
            JOptionPane.showMessageDialog(this, 
                    details.toString(), 
                    "Request Details", 
                    JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            System.err.println("Error displaying request details: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Find request by ID
    private MerchantWorkRequest findRequestById(String requestId) {
        // Remove prefix
        String productId = requestId.replace("REQ-", "");
        
        Business.EcoSystem system = Business.EcoSystem.getInstance();
        if (system.getWorkQueue() != null) {
            for (WorkRequest req : system.getWorkQueue().getWorkRequestList()) {
                if (req instanceof MerchantWorkRequest) {
                    MerchantWorkRequest merchantRequest = (MerchantWorkRequest) req;
                    if (merchantRequest.getProductId().equals(productId)) {
                        return merchantRequest;
                    }
                }
            }
        }
        return null;
    }
}
