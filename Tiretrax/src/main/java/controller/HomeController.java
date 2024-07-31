package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomeController {
    public AnchorPane HomePane;
    public Button btnAddItem;
    public Button BtnPurchaseOrder;
    public Button btnAddCustomer;

    public void btnAddItemOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) HomePane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/Stock.fxml")))));
        stage.centerOnScreen();
        stage.setTitle("TireTrax - Add Item");
        stage.setResizable(false);
        stage.show();
    }

    public void BtnPurchaseOrderOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) HomePane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/Invoice.fxml")))));
        stage.centerOnScreen();
        stage.setTitle("TireTrax - Purchase Order");
        stage.setResizable(false);
        stage.show();
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws IOException {
        System.out.println("Attempting to load AddCustomer.fxml");
        if (getClass().getResource("/../resources/View/AddCustomer.fxml") == null) {
            System.err.println("AddCustomer.fxml not found!");
        } else {
            System.out.println("AddCustomer.fxml found!");
        }
        Stage stage = (Stage) HomePane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../resources/View/AddCustomer.fxml")))));
        stage.centerOnScreen();
        stage.setTitle("TireTrax - Add Customer");
        stage.setResizable(false);
        stage.show();
    }
}
