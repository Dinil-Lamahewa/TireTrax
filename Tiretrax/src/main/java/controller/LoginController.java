package controller;

import db.DBConnection;
import dto.SystemUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    public AnchorPane loginPane;
    public TextField txtUserName;
    public TextField txtPassWord;
    public Button btnLogIn;

    public void btnLogInOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String enteredUsername = txtUserName.getText();
        String enteredPassword = txtPassWord.getText();

        SystemUsers authenticatedUser = authenticateUser(enteredUsername, enteredPassword);

        if (authenticatedUser != null) {
            if (authenticatedUser.getJobRole() == 0) {
                // Redirect to admin dashboard
//                redirectToDashboard("/view/AdminDashboard.fxml", "Admin Dashboard");
                Stage stage = (Stage) loginPane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/EmployeeDashBoard.fxml"))));
                stage.centerOnScreen();
                stage.setTitle("TireTrax - Login");
                stage.setResizable(false);
                stage.show();
            } else if (authenticatedUser.getJobRole() == 1) {
                // Redirect to employee dashboard
//                redirectToDashboard("/view/EmployeeDashboard.fxml", "Employee Dashboard");
                Stage stage = (Stage) loginPane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/AdminDashBoard.fxml"))));
                stage.centerOnScreen();
                stage.setTitle("TireTrax - Login");
                stage.setResizable(false);
                stage.show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "The username or password you entered is incorrect!").show();
        }
    }

    public ObservableList<SystemUsers> getSystemUser() throws SQLException, ClassNotFoundException {
        ObservableList<SystemUsers> userList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM systemUser";

        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet result = stm.executeQuery(sql);
        while (result.next()) {
            SystemUsers systemUsers = new SystemUsers(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getDate(5)
            );
            userList.add(systemUsers);
        }
        return userList;
    }

    private SystemUsers authenticateUser(String username, String password) throws SQLException, ClassNotFoundException {
        ObservableList<SystemUsers> usersList = getSystemUser();

        for (SystemUsers user : usersList) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Home.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("TireTrax");
        stage.setResizable(false);
        stage.show();
    }
}
