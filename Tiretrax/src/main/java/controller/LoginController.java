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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class LoginController {
    public AnchorPane loginPane;
    public TextField txtUserName;
    public TextField txtPassWord;
    public Button btnLogIn;

    public void BtnSignInOnAction(ActionEvent actionEvent)  throws SQLException, ClassNotFoundException, IOException {
        String enteredUsername = txtUserName.getText();
        String enteredPassword = txtPassWord.getText();

        SystemUsers authenticatedUser = authenticateUser(enteredUsername, enteredPassword);

        if (authenticatedUser != null) {
            if (authenticatedUser.getJobRole() == 0) {
                Stage stage = (Stage) loginPane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/EmployeeDashBoard.fxml"))));
                stage.centerOnScreen();
                stage.setTitle("TireTrax - Login");
                stage.setResizable(false);
                stage.show();
            } else if (authenticatedUser.getJobRole() == 1) {
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
        stage.setTitle("Welcome to the Tyre TraxHome");
        stage.setResizable(false);
        stage.show();
    }


    public void BtnResignOnAction(ActionEvent actionEvent) throws MessagingException {
        String receiveEmail = "dinillamahewa00@gmail.com";
        sendEmail(receiveEmail);
    }

    public void ForgertPasswordOnAction(MouseEvent mouseEvent) throws MessagingException {

    }

    private void sendEmail(String receiveEmail) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String sendEmail = "d.k.tireworks@gmail.com";
        String password = "ykdt tjht cjdk htvu";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendEmail,password);
            }
        });
        Message message = prepareMessage(session,sendEmail,receiveEmail,"Hii" );
        if(message!=null){
            new Alert(Alert.AlertType.INFORMATION,"SEND EMAIL").show();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Try again").show();
        }

        Transport.send(message);
    }

    private  Message prepareMessage(Session session, String sendEmail, String receiveEmail,String otp){

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{
                    new InternetAddress(receiveEmail)
            });

            message.setSubject("Message");
            message.setText(otp);

            return message;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
