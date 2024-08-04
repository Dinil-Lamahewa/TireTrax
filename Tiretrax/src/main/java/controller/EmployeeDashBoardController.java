package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeDashBoardController {
    public Button btnCustomer;
    public Button btnStock;
    public Button btnOrder;

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnOrder.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Home.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Home");
        stage.setResizable(false);
        stage.show();
    }

    public void CustomerbtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnCustomer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeAddCustomer.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Employee Add Customer");
        stage.setResizable(false);
        stage.show();
    }


    public void StockBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnOrder.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Stock.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Employee Stock");
        stage.setResizable(false);
        stage.show();
    }

    public void PurchaseOrderOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnOrder.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Invoice.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Invoice");
        stage.setResizable(false);
        stage.show();
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) {
    }

    public void btnSctockOnAction(ActionEvent actionEvent) {
    }

    public void btnOrderOnAction(ActionEvent actionEvent) {
    }
}
