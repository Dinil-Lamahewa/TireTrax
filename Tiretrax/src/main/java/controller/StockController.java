package controller;

import dto.tm.StockTm;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StockController {
    public AnchorPane AddItem;
    public TextField txtItemName;
    public TextField txtCategory;
    public TextField txtCompany;
    public TextField txtPurchurseQty;
    public TextField txtExpiredDate;
    public TextField txtPurchurseDate;
    public TextField txtPurchursePrice;
    public Button btnBack;
    public Button btnSave;
    public TableView<StockTm> tblCustomer;
    public TableColumn itemCode;
    public TableColumn itemname;
    public TableColumn category;
    public TableColumn company;
    public TableColumn purchurseqty;
    public TableColumn expireddate;
    public TableColumn sellingunitprice;
    public TableColumn purchursedate;
    public TableColumn purchurseprice;
    public TableColumn edit;
    public TableColumn delete;
    public Button SearBtn;
    public TextField txtSellingUnitPrice;


    public void initialize() {

    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AddItem.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Home.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("TireTrax - Add Item");
        stage.setResizable(false);
        stage.show();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

    }

    public void SearBtnOnAction(ActionEvent actionEvent) {

    }
}
