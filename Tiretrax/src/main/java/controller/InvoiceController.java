package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class InvoiceController {

    public Label InvoId;
    public DatePicker DateAndTimeId;
    public TextArea RemraksId;
    public TextField GrossAmountId;
    public TextField vatRateId;
    public TextField NetAmountId;
    public ComboBox CustomerId;
    public ComboBox ItemId;
    public TableView CartTblId;
    public TableColumn ItemNameTblId;
    public TableColumn QtyTblId;
    public TableColumn UnitPriceTblId;
    public TableColumn TotalPriceTblId;
    public Button PrintId;
    public Button ReturnPoId;

    public void PrintIdbtnOnAction(ActionEvent actionEvent) {

    }

    public void ReturnPOOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.WARNING, "Under The Maintenance").show();
    }


    public void BackBtnOnACTION(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) PrintId.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashBoard.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Home");
        stage.setResizable(false);
        stage.show();
    }
}
