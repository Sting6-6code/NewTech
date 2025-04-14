package Business;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author wangsiting
 */


import javax.swing.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartPanelBean extends JPanel {

    public ChartPanelBean() {
        // 创建一个简单的图表（可以先随便一个）
         try {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1, "Sales", "Jan");
        dataset.addValue(3, "Sales", "Feb");
        dataset.addValue(2, "Sales", "Mar");

        JFreeChart chart = ChartFactory.createBarChart(
                "Sample Chart", "Month", "Value", dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        this.setLayout(new java.awt.BorderLayout());
        this.add(chartPanel, java.awt.BorderLayout.CENTER);
    } catch (Exception e) {
        e.printStackTrace();
        JLabel errorLabel = new JLabel("Chart failed to load.");
        this.add(errorLabel);
    }}
}
