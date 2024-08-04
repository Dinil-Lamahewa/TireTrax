package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public AnchorPane loginPane;
    public TextField txtUserName;
    public TextField txtPassWord;
    public Button btnLogIn;

    public void btnLogInOnAction(ActionEvent actionEvent) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Home.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Welcome to the Tyre TraxHome");
        stage.setResizable(false);
        stage.show();
    }

    public void BtnResignOnAction(ActionEvent actionEvent) {
    }

    public void BtnSignInOnAction(ActionEvent actionEvent) {
    }

    public void ForgertPasswordOnAction(MouseEvent mouseEvent) {
    }
}
