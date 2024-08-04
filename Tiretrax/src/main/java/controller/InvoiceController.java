package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

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
    public CheckBox ReturnId;

    public void PrintIdbtnOnAction(ActionEvent actionEvent) {
    }

    public void ReturnPOOnAction(ActionEvent actionEvent) {
    }

    public void ReturnIdOnAction(ActionEvent actionEvent) {
    }
}
