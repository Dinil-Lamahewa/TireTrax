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
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/AdminDashBoard.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Admin - Login");
        stage.setResizable(false);
        stage.show();
    }

    public void BtnPurchaseOrderOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) HomePane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/Login.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Employee - Login");
        stage.setResizable(false);
        stage.show();
    }


}
