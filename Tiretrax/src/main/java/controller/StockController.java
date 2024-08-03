package controller;

import Model.StockModel;
import Model.impl.StockModelImpl;
import db.DBConnection;
import dto.Customer;
import dto.Stock;
import dto.tm.CustomerTm;
import dto.tm.StockTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import java.sql.*;
import java.time.LocalDate;

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
    public Button SearBtn;
    public TextField txtSellingUnitPrice;
    public DatePicker DateExpired;
    public Button Updatebtn;
    public Button ResetBtn;
    private StockModel stockModel = new StockModelImpl();
    public DatePicker Dayepurchursedate;
    public TableView<StockTm> tblCustomer;
    public TableColumn<StockTm, String> itemCode;
    public TableColumn<StockTm, String> itemname;
    public TableColumn<StockTm, String> category;
    public TableColumn<StockTm, String> company;
    public TableColumn<StockTm, String> purchurseqty;
    public TableColumn<StockTm, String> expireddate;
    public TableColumn<StockTm, String> sellingunitprice;
    public TableColumn<StockTm, String> purchursedate;
    public TableColumn<StockTm, String> purchurseprice;
    public TableColumn<StockTm, String> edit;
    public TableColumn<StockTm, String> delete;

    public void initialize() {
        itemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        itemname.setCellValueFactory(new PropertyValueFactory<>("Name"));
        category.setCellValueFactory(new PropertyValueFactory<>("Category"));
        company.setCellValueFactory(new PropertyValueFactory<>("Company"));
        purchurseqty.setCellValueFactory(new PropertyValueFactory<>("PurchaseQty"));
        expireddate.setCellValueFactory(new PropertyValueFactory<>("ExpiredDate"));
        sellingunitprice.setCellValueFactory(new PropertyValueFactory<>("SellingUnitPrice"));
        purchursedate.setCellValueFactory(new PropertyValueFactory<>("PurchaseDate"));
        purchurseprice.setCellValueFactory(new PropertyValueFactory<>("PurchasePrice"));
        edit.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        delete.setCellValueFactory(new PropertyValueFactory<>("Delete"));
        loadCustomerTable();
    }
    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AddItem.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Home.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("TireTrax - Add Item");
        stage.setResizable(false);
        stage.show();
    }
    private String generateItemId() throws SQLException, ClassNotFoundException {
        String customerId = null;
        String sql = "SELECT MAX(ItemCode) AS maxId FROM Stock";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String maxId = resultSet.getString("maxId");
            int idNumber = maxId != null ? Integer.parseInt(maxId.replace("IT", "")) : 0;
            customerId = String.format("IT%05d", idNumber + 1);
        } else {
            customerId = "IT00001";
        }

        return customerId;
    }
    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {

            //new Alert(Alert.AlertType.WARNING,"This Function Not Supported to Your Computer Please Contact Maintenance Team").show();
            String ItemCode = generateItemId();
            boolean isSaved = stockModel.saveStock(new Stock(
                    ItemCode,
                    txtItemName.getText(),
                    txtCategory.getText(),
                    txtCompany.getText(),
                    txtPurchurseQty.getText(),
                    Date.valueOf(DateExpired.getValue()),
                    txtSellingUnitPrice.getText(),
                    Date.valueOf(Dayepurchursedate.getValue()),
                    txtPurchursePrice.getText()

            ));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
                loadCustomerTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.WARNING,"There is a some issue in this system, Please Contact Maintenance Team").show();
        }
    }

    private void clearFields() {
        txtItemName.setText("");
        txtCategory.setText("");
        txtCompany.setText("");
        txtPurchursePrice.setText("");
        txtPurchurseQty.setText("");
        txtSellingUnitPrice.setText("");
        DateExpired.setValue(null);
        Dayepurchursedate.setValue(null);

    }

    private void loadCustomerTable() {
        ObservableList<StockTm> tmList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Stock";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet result = stm.executeQuery(sql);
            while (result.next()) {
                Button Ebtn = new Button("Edit");
                Button Dbtn = new Button("Delete");

                StockTm tm = new StockTm(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8),
                        result.getString(9),
                        Ebtn,
                        Dbtn
                );
                Stock stock = new Stock(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getDate(6),
                        result.getString(7),
                        result.getDate(8),
                        result.getString(9)
                );

                Dbtn.setOnAction(actionEvent -> deleteCustomer(tm.getItemCode()));

                Ebtn.setOnAction(event -> loadCustomerData(stock));

                tmList.add(tm);
            }

            tblCustomer.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(String itemCode) {
        try {
            boolean isDeleted = stockModel.deleteStock(itemCode);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                loadCustomerTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerData(Stock stock) {
        txtItemName.setText(stock.getName());
        txtCategory.setText(stock.getCategory());
        txtCompany.setText(stock.getCompany());
        DateExpired.setValue(LocalDate.parse(String.valueOf(stock.getExpiredDate())));
        txtPurchurseQty.setText(stock.getPurchaseQty());
        txtSellingUnitPrice.setText(stock.getSellingUnitPrice());
        Dayepurchursedate.setValue(LocalDate.parse(String.valueOf(stock.getPurchaseDate())));
        txtPurchursePrice.setText(stock.getPurchasePrice());
    }


    public void SearBtnOnAction(ActionEvent actionEvent) {

    }

    public void UpdatebtnOnAction(ActionEvent actionEvent) {
    }

    public void ResetBtnOnAction(ActionEvent actionEvent) {
        clearFields();
        loadCustomerTable();
    }
}
