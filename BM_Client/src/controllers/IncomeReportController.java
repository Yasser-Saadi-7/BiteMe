package controllers;

import javafx.fxml.FXML;
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
import entities.IncomeReport;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class IncomeReportController implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private TableView<IncomeReport> incomeTable;
    @FXML
    private TableColumn<IncomeReport, String> reportIdCol;
    @FXML
    private TableColumn<IncomeReport, String> branchIdCol;
    @FXML
    private TableColumn<IncomeReport, String> totalIncomeCol;
    @FXML
    private VBox chartVBox; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportIdCol.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        branchIdCol.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        totalIncomeCol.setCellValueFactory(new PropertyValueFactory<>("totalIncome"));
    }

    public void initData(ArrayList<IncomeReport> reports, String month, String year) {
        ObservableList<IncomeReport> incomeReports = FXCollections.observableArrayList(reports);
        incomeTable.setItems(incomeReports);

        // Create and populate the bar chart
        if (reports != null && !reports.isEmpty()) {
            createIncomeChart(reports.get(0), month, year);
        }
    }
    
    private void createIncomeChart(IncomeReport report, String month, String year) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Report");
        yAxis.setLabel("Total Income (ILS)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Total Income for " + month + "/" + year);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Income");

        double totalIncome = 0;
        try {
            totalIncome = Double.parseDouble(report.getTotalIncome());
        } catch (NumberFormatException e) {
            System.err.println("Could not parse total income value: " + report.getTotalIncome());
        }

        series.getData().add(new XYChart.Data<>("Branch " + report.getBranchId(), totalIncome));
        
        barChart.getData().add(series);
        
        // Add chart to the VBox
        chartVBox.getChildren().add(barChart);
    }

    @FXML
    private void handleCloseButton(ActionEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }
}