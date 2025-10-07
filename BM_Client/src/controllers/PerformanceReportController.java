package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import entities.PerformanceReport;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PerformanceReportController implements Initializable {

    @FXML
    private Button btnclose;
    @FXML
    private TableView<PerformanceReport> performanceTable;
    @FXML
    private TableColumn<PerformanceReport, String> reportIdCol, branchIdCol, reportDateCol, totalOrdersCol,
            totalRevenueCol, avgExpectedDeliveryCol, avgActualDeliveryCol, onTimeDeliveryRateCol;
    @FXML
    private VBox chartVBox; // Add this to your FXML: <VBox fx:id="chartVBox" />

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportIdCol.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        branchIdCol.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        totalOrdersCol.setCellValueFactory(new PropertyValueFactory<>("totalOrders"));
        totalRevenueCol.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
        avgExpectedDeliveryCol.setCellValueFactory(new PropertyValueFactory<>("averageExpectedDeliveryTime"));
        avgActualDeliveryCol.setCellValueFactory(new PropertyValueFactory<>("averageActualDeliveryTime"));
        onTimeDeliveryRateCol.setCellValueFactory(new PropertyValueFactory<>("onTimeDeliveryRate"));
    }
    
    public void initData(ArrayList<PerformanceReport> reports) {
        ObservableList<PerformanceReport> perfReports = FXCollections.observableArrayList(reports);
        performanceTable.setItems(perfReports);
        
        if (reports != null && !reports.isEmpty()) {
            createDeliveryTimeChart(reports.get(0));
        }
    }
    
    private void createDeliveryTimeChart(PerformanceReport report) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Delivery Time Type");
        yAxis.setLabel("Average Time (Minutes)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Average Delivery Time Comparison");
        barChart.setLegendVisible(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        try {
            double expectedTime = Double.parseDouble(report.getAverageExpectedDeliveryTime());
            double actualTime = Double.parseDouble(report.getAverageActualDeliveryTime());
            series.getData().add(new XYChart.Data<>("Expected", expectedTime));
            series.getData().add(new XYChart.Data<>("Actual", actualTime));
        } catch (NumberFormatException e) {
            System.err.println("Could not parse delivery time values.");
            return;
        }
        
        barChart.getData().add(series);
        
        chartVBox.getChildren().add(barChart);
    }

    @FXML
    private void handleCloseButton(ActionEvent event) {
        Stage currentStage = (Stage) btnclose.getScene().getWindow();
        currentStage.close();
    }
}