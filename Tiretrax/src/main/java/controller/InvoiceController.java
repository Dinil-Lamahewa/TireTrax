package controller;

import dto.Customer;
import dto.InvoiceDetails;
import dto.InvoiceMaster;
import dto.tm.InvoiceItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import db.DBConnection;
import model.InvoiceModel;
import model.impl.InvoiceModelImpl;

import java.io.IOException;
import java.sql.*;

public class InvoiceController {

    @FXML
    public Label InvoId;
    @FXML
    public DatePicker DateAndTimeId;
    @FXML
    public TextArea RemraksId;
    @FXML
    public TextField GrossAmountId;
    @FXML
    public TextField vatRateId;
    @FXML
    public TextField NetAmountId;
    @FXML
    public ComboBox<String> CustomerId;
    @FXML
    public ComboBox<String> ItemId;
    @FXML
    public TableView<InvoiceItem> CartTblId;
    @FXML
    public TableColumn<InvoiceItem, String> ItemNameTblId;
    @FXML
    public TableColumn<InvoiceItem, Double> UnitPriceTblId;
    @FXML
    public TableColumn<InvoiceItem, Integer> QtyTblId;
    @FXML
    public TableColumn<InvoiceItem, Double> TotalPriceTblId;
    @FXML
    public Button PrintId;
    @FXML
    public Button ReturnPoId;
    @FXML
    public Button BackBtn;

    private ObservableList<InvoiceItem> invoiceItems;
    private final InvoiceModel invoiceModel = new InvoiceModelImpl();
    public void initialize() throws SQLException, ClassNotFoundException {
        String id = genereateInvoNo();
        InvoId.setText(id);
        loadCustomerData();
        loadItemData();
        setupTable();
        setupListeners();
    }

    private void setupTable() {
        invoiceItems = FXCollections.observableArrayList();
        ItemNameTblId.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        UnitPriceTblId.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        QtyTblId.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        TotalPriceTblId.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        UnitPriceTblId.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        QtyTblId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        QtyTblId.setOnEditCommit(event -> {
            InvoiceItem item = event.getRowValue();
            item.setQuantity(event.getNewValue());
            updateAmounts();
        });

        UnitPriceTblId.setOnEditCommit(event -> {
            InvoiceItem item = event.getRowValue();
            item.setUnitPrice(event.getNewValue());
            updateAmounts();
        });

        CartTblId.setItems(invoiceItems);
        CartTblId.setEditable(true);
    }

    private void setupListeners() {
        vatRateId.textProperty().addListener((observable, oldValue, newValue) -> {
            updateAmounts();
        });
    }

    private void updateAmounts() {
        double grossAmount = invoiceItems.stream()
                .mapToDouble(InvoiceItem::getTotalPrice)
                .sum();
        GrossAmountId.setText(String.format("%.2f", grossAmount));

        String vatText = vatRateId.getText().trim();
        double vatRate = parseDouble(vatText, -1); // Use -1 to indicate an invalid or missing VAT rate

        if (vatRate < 0) {
            NetAmountId.setText(String.format("%.2f", grossAmount));
        } else {
            double netAmount = (grossAmount / 100) * vatRate;
            NetAmountId.setText(String.format("%.2f", netAmount));
        }
    }

    private double parseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void loadCustomerData() throws SQLException, ClassNotFoundException {
        ObservableList<String> customerList = FXCollections.observableArrayList();
        String sql = "SELECT Name FROM customer";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            customerList.add(name);
        }
        CustomerId.setItems(customerList);
    }

    private void loadItemData() throws SQLException, ClassNotFoundException {
        ObservableList<String> itemList = FXCollections.observableArrayList();
        String sql = "SELECT Name FROM stock";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            itemList.add(name);
        }
        ItemId.setItems(itemList);
    }

    public void ItemIdOnAction(ActionEvent actionEvent) {

        String selectedItem = ItemId.getValue();
        try {
            String sql = "SELECT SellingUnitPrice FROM stock WHERE Name = ?";
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, selectedItem);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double unitPrice = resultSet.getDouble("SellingUnitPrice");
                InvoiceItem item = new InvoiceItem(selectedItem, unitPrice);
                invoiceItems.add(item);
                CartTblId.refresh();
                updateAmounts();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void PrintIdbtnOnAction(ActionEvent actionEvent) {
        try{
        Boolean  isSaved= invoiceModel.saveInvoice(new InvoiceMaster(
                InvoId.getText(),
                CustomerId.getValue(),
                Date.valueOf(DateAndTimeId.getValue()),
                RemraksId.getText(),
                Double.valueOf( GrossAmountId.getText()),
                Integer.parseInt(vatRateId.getText()),
                Double.valueOf(NetAmountId.getText()),
                1


        ),new InvoiceDetails(

        ));

            if (isSaved) {
        new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();

        clearFields();
            }
        } catch (
        SQLIntegrityConstraintViolationException ex) {
                new Alert(Alert.AlertType.ERROR, "Duplicate Entry").show();
                } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                }
    }

    private void clearFields() {
    }

    public void ReturnPOOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.WARNING, "Under Maintenance").show();
    }

    public void BackBtnOnACTION(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) PrintId.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashBoard.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Home");
        stage.setResizable(false);
        stage.show();
    }

    private String genereateInvoNo() throws SQLException, ClassNotFoundException {
        String customerId = null;
        String sql = "SELECT MAX(InvoNumber) AS maxId FROM invoicemaster";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String maxId = resultSet.getString("maxId");
            int idNumber = maxId != null ? Integer.parseInt(maxId.replace("INV", "")) : 0;
            customerId = String.format("INV%05d", idNumber + 1);
        } else {
            customerId = "INV00001";
        }
        return customerId;
    }
}
