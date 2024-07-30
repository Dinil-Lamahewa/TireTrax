package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    public AnchorPane HomePane;
    public Button btnAdmin;
    public Button btnEmployee;

    public void btnAdminOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) HomePane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Login.fxml"))));
            stage.setTitle("Admin Login");
            stage.show();
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) HomePane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Login.fxml"))));
            stage.setTitle("Employee Login");
            stage.show();
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(e);
        }
    }
}
