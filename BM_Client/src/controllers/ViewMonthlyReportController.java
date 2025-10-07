package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import entities.Message1;
import entities.MessageType;
import entities.IncomeReport;
import entities.OrderReport;
import entities.PerformanceReport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Consumer;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class ViewMonthlyReportController {

    // ADDED DECLARATION for chooseBranchcombo to prevent NullPointerException
    @FXML
    private ComboBox<String> chooseBranchcombo; 

    @FXML
    private ComboBox<String> chooseMocombo;

    @FXML
    private ComboBox<String> chooseYrcombo;

    // ADDED DECLARATION for the new Back button
    @FXML
    private Button btnBack; 

    @FXML
    private Button viewInRepobtn, viewOrderRepobtn, viewPerfRepobtn, btnclose;

    @FXML
    private void initialize() {
        populateMonthComboBox();
        populateYearComboBox();
        setDefaults();
    }

    private void populateMonthComboBox() {
        chooseMocombo.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        chooseMocombo.setPromptText("Month");
    }

    private void populateYearComboBox() {
        int startYear = 2010;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = startYear; year <= currentYear; year++) {
            chooseYrcombo.getItems().add(String.valueOf(year));
        }
        chooseYrcombo.setPromptText("Year");
    }

    private void setDefaults() {
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);
        chooseMocombo.setValue(String.valueOf(nowMonth));
        chooseYrcombo.setValue(String.valueOf(nowYear));
    }

    /*
     *
     */
    @FXML
    private void handleViewPerformanceReportButton(ActionEvent event) {
        if (!isInputValid()) return;
        String requestData = ChatClient.logIn.getHomeBranchId() + " " + chooseMocombo.getValue() + " " + chooseYrcombo.getValue();
        disableReportButtons(true);

        requestReportSync(MessageType.getPerformanceReports, requestData, (reports) -> {
            @SuppressWarnings("unchecked")
            ArrayList<PerformanceReport> performanceReports = (ArrayList<PerformanceReport>) reports;
            Platform.runLater(() -> openPerformanceReportPage(performanceReports));
        }, () -> disableReportButtons(false));
    }

    @FXML
    private void handleViewIncomeReportButton(ActionEvent event) {
        if (!isInputValid()) return;
        String requestData = ChatClient.logIn.getHomeBranchId() + " " + chooseMocombo.getValue() + " " + chooseYrcombo.getValue();
        disableReportButtons(true);

        requestReportSync(MessageType.getIncomeReports, requestData, (reports) -> {
            @SuppressWarnings("unchecked")
            ArrayList<IncomeReport> incomeReports = (ArrayList<IncomeReport>) reports;
            Platform.runLater(() -> openIncomeReportPage(incomeReports));
        }, () -> disableReportButtons(false));
    }

    @FXML
    private void handleViewOrderReportButton(ActionEvent event) {
        if (!isInputValid()) return;
        String requestData = ChatClient.logIn.getHomeBranchId() + " " + chooseMocombo.getValue() + " " + chooseYrcombo.getValue();
        disableReportButtons(true);

        requestReportSync(MessageType.getOrderReports, requestData, (reports) -> {
            @SuppressWarnings("unchecked")
            ArrayList<OrderReport> orderReports = (ArrayList<OrderReport>) reports;
            Platform.runLater(() -> openOrderReportPage(orderReports));
        }, () -> disableReportButtons(false));
    }

    /**
     *
     * @param type message type to send
     * @param requestData data payload
     * @param onSuccess consumer that gets the retrieved report list (ArrayList<T>)
     * @param onFinally runnable executed after success or failure (re-enables UI)
     */
    private void requestReportSync(MessageType type, String requestData, Consumer<Object> onSuccess, Runnable onFinally) {
        if (ChatClient.logIn == null || ClientUI.chat == null) {
            runAlertLater(Alert.AlertType.ERROR, "Connection error", "Not connected or not logged in.");
            if (onFinally != null) onFinally.run();
            return;
        }

        Task<Object> task = new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                if (type == MessageType.getPerformanceReports) {
                    ChatClient.performanceReportsList = null;
                } else if (type == MessageType.getIncomeReports) {
                    ChatClient.incomeReportsList = null;
                } else if (type == MessageType.getOrderReports) {
                    ChatClient.orderReportsList = null;
                }

                Message1 request = new Message1(type, requestData);

                ClientUI.chat.accept(request);

                if (type == MessageType.getPerformanceReports) {
                    return ChatClient.performanceReportsList;
                } else if (type == MessageType.getIncomeReports) {
                    return ChatClient.incomeReportsList;
                } else if (type == MessageType.getOrderReports) {
                    return ChatClient.orderReportsList;
                }
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            try {
                Object reports = task.getValue();

                if (reports != null && !((ArrayList<?>) reports).isEmpty()) {
                    onSuccess.accept(reports);
                } else if (reports != null && ((ArrayList<?>) reports).isEmpty()) {
                    runAlertLater(Alert.AlertType.INFORMATION, "Information", "No report data found for the selected period.");
                } else {
                    runAlertLater(Alert.AlertType.ERROR, "Error", "Failed to retrieve report data.");
                }
            } catch (ClassCastException cce) {
                cce.printStackTrace();
                runAlertLater(Alert.AlertType.ERROR, "Error", "Unexpected data format received from server.");
            } catch (Exception e) {
                e.printStackTrace();
                runAlertLater(Alert.AlertType.ERROR, "Error", "An error occurred while processing the report.");
            } finally {
                if (onFinally != null) onFinally.run();
            }
        });

        task.setOnFailed(event -> {
            Throwable e = task.getException();
            e.printStackTrace();
            runAlertLater(Alert.AlertType.ERROR, "Error", "Failed to send request or network error: " + e.getMessage());
            if (onFinally != null) onFinally.run();
        });

        new Thread(task).start();
    }


    private void openIncomeReportPage(ArrayList<IncomeReport> reports) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundry/IncomeReport.fxml"));
            Parent root = loader.load();

            IncomeReportController controller = loader.getController();
            controller.initData(reports, chooseMocombo.getValue(), chooseYrcombo.getValue());

            Stage reportStage = new Stage();
            reportStage.setScene(new Scene(root));
            reportStage.setTitle("Income Report");
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            runAlertLater(Alert.AlertType.ERROR, "Error", "Failed to open the report page.");
        }
    }

    private void openOrderReportPage(ArrayList<OrderReport> reports) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundry/OrderReport.fxml"));
            Parent root = loader.load();

            OrderReportController controller = loader.getController();
            controller.initData(reports);

            Stage reportStage = new Stage();
            reportStage.setScene(new Scene(root));
            reportStage.setTitle("Order Report");
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            runAlertLater(Alert.AlertType.ERROR, "Error", "Failed to open the report page.");
        }
    }

    private void openPerformanceReportPage(ArrayList<PerformanceReport> reports) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundry/PerformanceReport.fxml"));
            Parent root = loader.load();

            PerformanceReportController controller = loader.getController();
            controller.initData(reports);

            Stage reportStage = new Stage();
            reportStage.setScene(new Scene(root));
            reportStage.setTitle("Performance Report");
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            runAlertLater(Alert.AlertType.ERROR, "Error", "Failed to open the report page.");
        }
    }

    private boolean isInputValid() {
        if (chooseMocombo.getValue() == null || chooseYrcombo.getValue() == null) {
            runAlertLater(Alert.AlertType.WARNING, "Invalid Input", "Please select both month and year.");
            return false;
        }
        return true;
    }

    private void runAlertLater(Alert.AlertType type, String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private void disableReportButtons(boolean disable) {
        Platform.runLater(() -> {
            viewInRepobtn.setDisable(disable);
            viewOrderRepobtn.setDisable(disable);
            viewPerfRepobtn.setDisable(disable);
        });
    }

    // ADDED HANDLER FOR THE BACK BUTTON
    @FXML
    private void handleBackButton(ActionEvent event) {
        // This button does the same as the "Log Out" button (returns to the main menu)
        handleCloseButton(event);
    }
    
    @FXML
    private void handleCloseButton(ActionEvent event) {
        openFXML("/boundry/BranchManagerPage.fxml", "Branch Manager", (Stage)((Node) event.getSource()).getScene().getWindow());
    }

    private void openFXML(String fxmlFile, String title, Stage currentStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(title);
            newStage.show();
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            runAlertLater(Alert.AlertType.ERROR, "Error", "Failed to open page: " + fxmlFile);
        }
    }
}