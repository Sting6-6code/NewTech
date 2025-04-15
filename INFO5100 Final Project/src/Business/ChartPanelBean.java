package Business;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import javax.swing.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import Business.Product.SalesReportModel;

/**
 * 图表面板组件，用于显示销售报表图表
 * @author wangsiting
 */
public class ChartPanelBean extends JPanel {
    
    private ChartPanel chartPanel;
    private JFreeChart chart;
    private DefaultCategoryDataset dataset;
    
    /**
     * 创建一个带有示例数据的图表
     */
    public ChartPanelBean() {
        initializeWithSampleData();
    }
    
    /**
     * 使用示例数据初始化图表
     */
    private void initializeWithSampleData() {
        try {
            dataset = new DefaultCategoryDataset();
            dataset.addValue(1000.0, "Sales", "Sample");
            
            createBarChart("Sales Overview", "Period", "Amount ($)");
            setupChartPanel();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage(e.getMessage());
        }
    }
    
    private void createBarChart(String title, String xLabel, String yLabel) {
        chart = ChartFactory.createBarChart(
                title,
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // 设置图表样式
        chart.setBackgroundPaint(Color.white);
        
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        // 设置柱状图样式
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(79, 129, 189)); // 蓝色
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
    }
    
    private void setupChartPanel() {
        if (chartPanel != null) {
            this.remove(chartPanel);
        }
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));
        this.setLayout(new BorderLayout());
        this.add(chartPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    /**
     * 使用销售报表模型更新图表
     * @param model 销售报表数据模型
     */
    public void updateChart(SalesReportModel model) {
        if (model == null) {
            return;
        }
        
        try {
            dataset.clear();
            Map<String, Double> monthlySales = model.getMonthlySales();
            
            if (monthlySales.isEmpty()) {
                showNoDataMessage();
                return;
            }
            
            // 添加销售数据
            for (Map.Entry<String, Double> entry : monthlySales.entrySet()) {
                dataset.addValue(entry.getValue(), "Monthly Sales", entry.getKey());
            }
            
            // 更新图表标题和样式
            String timeRange = String.format("Sales Report (%s - %s)", 
                    model.getStartDate().toString(),
                    model.getEndDate().toString());
            
            createBarChart(timeRange, "Date", "Sales Amount ($)");
            setupChartPanel();
            
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage(e.getMessage());
        }
    }
    
    /**
     * 切换图表类型为柱状图
     */
    public void setBarChart() {
        if (dataset != null) {
            createBarChart(chart.getTitle().getText(), "Date", "Sales Amount ($)");
            setupChartPanel();
        }
    }
    
    /**
     * 切换图表类型为折线图
     */
    public void setLineChart() {
        if (dataset != null) {
            chart = ChartFactory.createLineChart(
                    chart.getTitle().getText(),
                    "Date",
                    "Sales Amount ($)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );
            setupChartPanel();
        }
    }
    
    /**
     * 显示无数据消息
     */
    private void showNoDataMessage() {
        this.removeAll();
        JLabel noDataLabel = new JLabel("No sales data available for the selected period", JLabel.CENTER);
        noDataLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.setLayout(new BorderLayout());
        this.add(noDataLabel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    /**
     * 显示错误消息
     * @param message 错误信息
     */
    private void showErrorMessage(String message) {
        this.removeAll();
        JLabel errorLabel = new JLabel("Error loading chart: " + message, JLabel.CENTER);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        errorLabel.setForeground(Color.RED);
        this.setLayout(new BorderLayout());
        this.add(errorLabel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    /**
     * 获取当前图表
     * @return 当前JFreeChart对象
     */
    public JFreeChart getChart() {
        return chart;
    }
}