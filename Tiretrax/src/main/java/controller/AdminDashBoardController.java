package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashBoardController {
    public AnchorPane AdminDashBoardPane;
    //public Button btnCreateEmployeeAccounts;
    //public Button btnViewCustomers;
    public Button viewStock;
   // public Button viewReports;

//    public void btnCreateEmployeeAccountsOnAction(ActionEvent actionEvent) {
//    }
//
//    public void btnViewCustomers(ActionEvent actionEvent) {
//    }

    public void viewStockOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AdminDashBoardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/AdminStock.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("View Admin Stock");
        stage.setResizable(false);
        stage.show();
    }

//    public void viewReportsOnAction(ActionEvent actionEvent) {
//
//    }

    public void EmployeeACCBtnnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AdminDashBoardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Employee Account");
        stage.setResizable(false);
        stage.show();
    }

    public void CustomerBaseBtnnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AdminDashBoardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("View Customer Base");
        stage.setResizable(false);
        stage.show();
    }

    public void viewReportOnAction(ActionEvent actionEvent) throws IOException {
//        Stage stage = (Stage) AdminDashBoardPane.getScene().getWindow();
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/Report.fxml"))));
//        stage.centerOnScreen();
//        stage.setTitle("View Report");
//        stage.setResizable(false);
//        stage.show();
    }

    public void invoiceBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AdminDashBoardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/Invoice.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("View Invoice");
        stage.setResizable(false);
        stage.show();

    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AdminDashBoardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Home.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("TireTrax");
        stage.setResizable(false);
        stage.show();
    }
}
