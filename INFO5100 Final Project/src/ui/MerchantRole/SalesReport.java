/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.MerchantRole;

import Business.Product.SalesRecord;
import Business.Product.SalesRecordDirectory;
import Business.Product.SalesReportModel;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.CardLayout;
import javax.swing.JPanel;
import java.util.Calendar;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JTextArea;

/**
 *
 * @author wangsiting
 */
public class SalesReport extends javax.swing.JPanel {

    private SalesRecordDirectory salesRecordDirectory;
    private SalesReportModel reportModel;
    private JPanel userProcessContainer;

    /**
     * Creates new form SalesReport
     */
    public SalesReport(JPanel userProcessContainer) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.salesRecordDirectory = new SalesRecordDirectory();
        this.reportModel = new SalesReportModel();
        
        // Apply unified UI theme
        setupTheme();
    }
    
    /**
     * Apply consistent UI theme to all components
     */
    private void setupTheme() {
        // Set panel background color
        this.setBackground(new Color(240, 245, 255));
        chartPanel.setBackground(new Color(240, 245, 255));
        
        // Style all buttons
        styleAllButtons();
        
        // Style all labels
        styleAllLabels();
        
        // Style text area
        styleTextArea(jTextArea1);
    }
    
    /**
     * Apply consistent styling to all buttons
     */
    private void styleAllButtons() {
        styleButton(btnBack);
        styleButton(btnGenerate);
        styleButton(btnExport);
        styleButton(btnPrint);
        styleButton(btnSwitchChart);
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
        button.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    }
    
    /**
     * Apply consistent styling to all labels
     */
    private void styleAllLabels() {
        // Style title labels
        styleTitleLabel(lblTitle);
        styleTitleLabel(lblTitle1);
        styleTitleLabel(lblTitle2);
        
        // Style regular labels
        styleLabel(lblFrom);
        styleLabel(lblTo);
    }
    
    /**
     * Apply title label styling
     * @param label Label to style
     */
    private void styleTitleLabel(JLabel label) {
        label.setForeground(new Color(26, 79, 156));
        label.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
    }
    
    /**
     * Apply regular label styling
     * @param label Label to style
     */
    private void styleLabel(JLabel label) {
        label.setForeground(new Color(26, 79, 156));
        label.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
    }
    
    /**
     * Style text area with consistent theme
     * @param textArea TextArea to style
     */
    private void styleTextArea(JTextArea textArea) {
        textArea.setBackground(new Color(245, 245, 250)); // Light gray-white background
        textArea.setForeground(new Color(13, 25, 51)); // Dark blue text
        textArea.setCaretColor(new Color(26, 79, 156)); // Medium blue cursor
        textArea.setBorder(BorderFactory.createLineBorder(new Color(90, 141, 224), 1));
        textArea.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cSV1 = new org.jfree.data.io.CSV();
        cSV2 = new org.jfree.data.io.CSV();
        startDateChooser = new com.toedter.calendar.JDateChooser();
        btnBack = new javax.swing.JButton();
        lblFrom = new javax.swing.JLabel();
        lblTo = new javax.swing.JLabel();
        endDateChooser = new com.toedter.calendar.JDateChooser();
        reportArea = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        lblTitle = new javax.swing.JLabel();
        btnGenerate = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        chartPanel = new javax.swing.JPanel();
        chartPanelBean1 = new Business.ChartPanelBean();
        lblTitle1 = new javax.swing.JLabel();
        lblTitle2 = new javax.swing.JLabel();
        btnSwitchChart = new javax.swing.JButton();

        startDateChooser.setDateFormatString("yyyy-MM-dd");

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblFrom.setText("📅 Start Date");

        lblTo.setText("📅 End Date");

        endDateChooser.setDateFormatString("yyyy-MM-dd");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        reportArea.setViewportView(jScrollPane1);

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Sales Report Page");

        btnGenerate.setText("🔘 Generate Report");
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });

        btnExport.setText("📤 Export CSV");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnPrint.setText("🖨 Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chartPanelLayout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(chartPanelBean1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(921, Short.MAX_VALUE))
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chartPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chartPanelBean1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblTitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setText(" 📊 Chart Panel");

        lblTitle2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitle2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle2.setText("📄 Report Area");

        btnSwitchChart.setText("📊 Bar Chart");
        btnSwitchChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSwitchChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFrom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(startDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(lblTo)
                        .addGap(18, 18, 18)
                        .addComponent(endDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnSwitchChart, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblTitle2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reportArea))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(endDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBack)
                        .addComponent(lblFrom)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTitle2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(reportArea, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSwitchChart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed

        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        // TODO add your handling code here:
        if (startDateChooser.getDate() == null || endDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select both start and end dates");
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = sdf.format(startDateChooser.getDate());
        String end = sdf.format(endDateChooser.getDate());
        
        // Generate sample data
        generateSampleData();
        
        // Create report model
        List<SalesRecord> salesRecords = salesRecordDirectory.getSalesRecordList();
        reportModel = new SalesReportModel(salesRecords, startDateChooser.getDate(), endDateChooser.getDate());
        
        // Generate report text
        StringBuilder report = new StringBuilder();
        report.append("Sales Report from ").append(start).append(" to ").append(end).append("\n\n");
        
        // Add total sales
        report.append("Total Sales: $").append(String.format("%.2f", reportModel.getTotalSales())).append("\n\n");
        
        // Add daily sales data
        report.append("Daily Sales:\n");
        Map<Date, Double> dailySales = reportModel.getDailySales();
        for (Map.Entry<Date, Double> entry : dailySales.entrySet()) {
            report.append(sdf.format(entry.getKey()))
                 .append(": $")
                 .append(String.format("%.2f", entry.getValue()))
                 .append("\n");
        }
        
        // Add product sales data
        report.append("\nSales by Product:\n");
        Map<String, Double> productSales = reportModel.getSalesByProduct();
        for (Map.Entry<String, Double> entry : productSales.entrySet()) {
            String productId = entry.getKey();
            String productName = "Unknown";
            // Find product name from the first matching record
            List<SalesRecord> matchingRecords = salesRecordDirectory.findSalesRecordsByProductId(productId);
            if (!matchingRecords.isEmpty()) {
                productName = matchingRecords.get(0).getProductName();
            }
            report.append(productName)
                 .append(" (").append(productId).append(")")
                 .append(": $")
                 .append(String.format("%.2f", entry.getValue()))
                 .append("\n");
        }
        
        jTextArea1.setText(report.toString());
        
        // Update chart
        chartPanelBean1.updateChart(reportModel);
    }//GEN-LAST:event_btnGenerateActionPerformed

    /**
     * Generate sample sales data for demonstration
     */
    private void generateSampleData() {
        // Clear existing records
        salesRecordDirectory = new SalesRecordDirectory();
        
        // Add sample records with today's date
        Date today = startDateChooser.getDate(); // Use selected date for sample data
        
        // Add various products with different quantities and prices
        salesRecordDirectory.createSalesRecord("P001", "Laptop", 999.99, 2, today, "C001");
        salesRecordDirectory.createSalesRecord("P002", "Mouse", 49.99, 5, today, "C002");
        salesRecordDirectory.createSalesRecord("P003", "Keyboard", 89.99, 3, today, "C003");
        salesRecordDirectory.createSalesRecord("P004", "Monitor", 299.99, 2, today, "C001");
        salesRecordDirectory.createSalesRecord("P005", "Headphones", 129.99, 4, today, "C004");
        
        // Add some records from previous day if date range allows
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();
        
        if (!yesterday.before(startDateChooser.getDate())) {
            salesRecordDirectory.createSalesRecord("P001", "Laptop", 999.99, 1, yesterday, "C005");
            salesRecordDirectory.createSalesRecord("P006", "Smartphone", 799.99, 3, yesterday, "C006");
        }
    }

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
        if (jTextArea1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please generate report first");
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }
            
            try (FileWriter writer = new FileWriter(filePath)) {
                String[] lines = jTextArea1.getText().split("\n");
                for (String line : lines) {
                    // Remove currency symbols and other special characters
                    line = line.replace("$", "")
                             .replace(":", ",")
                             .replace(" ", ",")
                             .trim();
                    if (!line.isEmpty()) {
                        writer.write(line + "\r\n");
                    }
                }
                JOptionPane.showMessageDialog(this, "Report exported successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error exporting report: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
        try {
            boolean complete = jTextArea1.print();
            if (complete) {
                JOptionPane.showMessageDialog(this, "Print completed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Print was cancelled");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error printing: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnSwitchChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSwitchChartActionPerformed
        // TODO add your handling code here:
        if (btnSwitchChart.getText().equals("📊 Bar Chart")) {
        chartPanelBean1.setBarChart();
        btnSwitchChart.setText("📈 Line Chart");
    } else {
        chartPanelBean1.setLineChart();
        btnSwitchChart.setText("📊 Bar Chart");
    }
        
        
    }//GEN-LAST:event_btnSwitchChartActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSwitchChart;
    private org.jfree.data.io.CSV cSV1;
    private org.jfree.data.io.CSV cSV2;
    private javax.swing.JPanel chartPanel;
    private Business.ChartPanelBean chartPanelBean1;
    private com.toedter.calendar.JDateChooser endDateChooser;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblFrom;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JLabel lblTo;
    private javax.swing.JScrollPane reportArea;
    private com.toedter.calendar.JDateChooser startDateChooser;
    // End of variables declaration//GEN-END:variables
}
