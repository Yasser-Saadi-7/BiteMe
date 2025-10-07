package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import entities.OrderReport;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OrderReportController implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private TableView<OrderReport> orderTable;
    @FXML
    private TableColumn<OrderReport, String> reportIdColumn, branchIdColumn, restaurantNameColumn,
            itemIdColumn, itemNameColumn, itemCategoryColumn, quantityColumn, itemPriceColumn, totalRevenueColumn;
    @FXML
    private VBox chartVBox; // Add this to your FXML: <VBox fx:id="chartVBox" />

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        branchIdColumn.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        restaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        totalRevenueColumn.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
    }
    
    public void initData(ArrayList<OrderReport> reports) {
        ObservableList<OrderReport> orderReports = FXCollections.observableArrayList(reports);
        orderTable.setItems(orderReports);
        
        if (reports != null && !reports.isEmpty()) {
            createCategoryPieChart(reports);
        }
    }
    
    private void createCategoryPieChart(ArrayList<OrderReport> reports) {
        // Group by category and sum the total revenue for each category
        Map<String, Double> revenueByCategory = reports.stream()
                .collect(Collectors.groupingBy(
                        OrderReport::getItemCategory,
                        Collectors.summingDouble(report -> {
                            try {
                                return Double.parseDouble(report.getTotalRevenue());
                            } catch (NumberFormatException e) {
                                return 0.0;
                            }
                        })
                ));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        revenueByCategory.forEach((category, totalRevenue) -> {
            pieChartData.add(new PieChart.Data(category, totalRevenue));
        });

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Revenue by Item Category");
        
        chartVBox.getChildren().add(pieChart);
    }

    @FXML
    private void handleCloseButton(ActionEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }
}