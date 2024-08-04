package controller;
import Model.CustomerModel;
import Model.impl.CustomerModelImpl;
import db.DBConnection;
import dto.Customer;
import dto.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class AddCustomerController {
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtEmail;
    public TextField txtCreditLimit;
    public TextField txtCreditPeriod;
    public ComboBox<String> cmbCustomerType;
    public Button btnBack;
    public Button btnSave;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn<CustomerTm, String> cusId;
    public TableColumn<CustomerTm, String> name;
    public TableColumn<CustomerTm, String> contact;
    public TableColumn<CustomerTm, String> email;
    public TableColumn<CustomerTm, String> address;
    public TableColumn<CustomerTm, String> type;
    public TableColumn<CustomerTm, String> creditLimit;
    public TableColumn<CustomerTm, String> creditPeriod;
    public TableColumn<CustomerTm, Void> editColumn;
    public AnchorPane AddCustomer;
    public Button SearBtn;
    public TableColumn<CustomerTm, Void> DeleteColumn;
    private final CustomerModel customerModel = new CustomerModelImpl();
    public Button btnUpdate;
    public Button btnreset;
    public Label lblCreditPeriod;
    public Label lblcreditLimit;
    private String updateCusID;

    public void initialize() {
        cusId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        email.setCellValueFactory(new PropertyValueFactory<>("Email"));
        address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        creditLimit.setCellValueFactory(new PropertyValueFactory<>("CreditLimit"));
        creditPeriod.setCellValueFactory(new PropertyValueFactory<>("CreditPeriod"));
        editColumn.setCellValueFactory(new PropertyValueFactory<>("edit"));
        DeleteColumn.setCellValueFactory(new PropertyValueFactory<>("Delete"));
        cmbCustomerType.setItems(FXCollections.observableArrayList("Regular", "On-time"));
        loadCustomerTable();
        updateButtonStates();
//        tblCustomer.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) ->{
//            setData((CustomerTm)newValue);
//        } ));
    }

//    private void setData(CustomerTm newValue) {
//        if(newValue!=null) {
//            String[] nameParts =newValue.getName().split(" ", 2);
//            txtFirstName.setText(nameParts.length > 0 ? nameParts[0] : "");
//            txtLastName.setText(nameParts.length > 1 ? nameParts[1] : "");
//            txtAddress.setText(newValue.getAddress());
//            txtContact.setText(newValue.getContact());
//            txtEmail.setText(newValue.getEmail());
//            cmbCustomerType.setItems(FXCollections.observableArrayList(newValue.getType()));
//            txtCreditLimit.setText(String.valueOf(newValue.getCreditLimit()));
//            txtCreditPeriod.setText(newValue.getCreditPeriod());
//        }
//    }

    private void loadCustomerData(Customer customer, String customerID) {
        String[] nameParts = customer.getName().split(" ", 2);
        txtFirstName.setText(nameParts.length > 0 ? nameParts[0] : "");
        txtLastName.setText(nameParts.length > 1 ? nameParts[1] : "");
        txtAddress.setText(customer.getAddress());
        txtContact.setText(customer.getContact());
        txtEmail.setText(customer.getEmail());
        txtCreditLimit.setText(String.valueOf(customer.getCreditLimit()));
        txtCreditPeriod.setText(customer.getCreditPeriod());
        cmbCustomerType.setValue(customer.getType());
        updateButtonStates();
        updateCusID = customerID;
    }
    private void updateButtonStates() {
        boolean fieldsFilled = !txtFirstName.getText().isEmpty()
                && !txtLastName.getText().isEmpty()
                && !txtAddress.getText().isEmpty()
                && !txtContact.getText().isEmpty()
                && !txtEmail.getText().isEmpty();

        btnSave.setDisable(fieldsFilled );
        btnUpdate.setDisable(!fieldsFilled);
    }

    private String generateCustomerId() throws SQLException, ClassNotFoundException {
        String customerId = null;
        String sql = "SELECT MAX(CustomerId) AS maxId FROM Customer";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String maxId = resultSet.getString("maxId");
            int idNumber = maxId != null ? Integer.parseInt(maxId.replace("CS", "")) : 0;
            customerId = String.format("CS%05d", idNumber + 1);
        } else {
            customerId = "CS00001";
        }
        return customerId;
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AddCustomer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashBoard.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("TireTrax");
        stage.setResizable(false);
        stage.show();
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Customer";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet result = stm.executeQuery(sql);

            while (result.next()) {
               Button btnEdit = new Button("Edit");
               Button btnDelete = new Button("Delete");

                CustomerTm tm = new CustomerTm(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getDouble(7),
                        result.getString(8),
                        btnEdit,
                        btnDelete
                );
                System.out.println(result.getString(1));
                Customer customer = new Customer(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getString(6),
                        result.getDouble(7),
                        result.getString(8)
                );

                btnDelete.setOnAction(actionEvent -> deleteCustomer(tm.getCustomerId()));
                btnEdit.setOnAction(event -> loadCustomerData(customer , tm.getCustomerId() ));
                tmList.add(tm);
            }

            tblCustomer.setItems(tmList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(String CustomerId) {
        try {
            boolean isDeleted = customerModel.deleteCustomer(CustomerId);
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

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            txtCreditPeriod.setVisible(true);
            txtCreditLimit.setVisible(true);
            String Cmb=cmbCustomerType.getValue();
            String CusId = generateCustomerId();
            boolean isSaved = false;
            if(Cmb.equals("On-time")){
                isSaved = customerModel.saveCustomer(new Customer(
                        CusId,
                        txtFirstName.getText() + " " + txtLastName.getText(),
                        txtContact.getText(),
                        txtEmail.getText(),
                        txtAddress.getText(),
                        cmbCustomerType.getValue(),
                        0,
                        ""
                ));
            }else{
             isSaved = customerModel.saveCustomer(new Customer(
                    CusId,
                    txtFirstName.getText() + " " + txtLastName.getText(),
                    txtContact.getText(),
                    txtEmail.getText(),
                    txtAddress.getText(),
                    cmbCustomerType.getValue(),
                    Double.parseDouble(txtCreditLimit.getText()),
                    txtCreditPeriod.getText()
            ));
             }
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved!").show();
                loadCustomerTable();
                clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void SearBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet result = customerModel.getCustomerByContact(txtContact.getText());
        System.out.println(result);
    }

    private void clearFields() {
        txtFirstName.clear();
        txtLastName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
        txtCreditLimit.clear();
        txtCreditPeriod.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try{
            boolean isUpdate=false;
            String Cmb=cmbCustomerType.getValue();
            if(Cmb.equals("On-time")) {
                isUpdate = customerModel.updateCustomer(new Customer(
                        updateCusID,
                        txtFirstName.getText() + " " + txtLastName.getText(),
                        txtContact.getText(),
                        txtEmail.getText(),
                        txtAddress.getText(),
                        cmbCustomerType.getValue(),
                       0,
                        ""
                ));
            }else{
                isUpdate = customerModel.updateCustomer(new Customer(
                        updateCusID,
                        txtFirstName.getText() + " " + txtLastName.getText(),
                        txtContact.getText(),
                        txtEmail.getText(),
                        txtAddress.getText(),
                        cmbCustomerType.getValue(),
                        Double.parseDouble(txtCreditLimit.getText()),
                        txtCreditPeriod.getText()
                ));
            }
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Update!").show();
                loadCustomerTable();
                clearFields();
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Something went wrong!").show();
                clearFields();
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        };
    }

    public void btnresetOnAction(ActionEvent actionEvent) {
        clearFields();
        loadCustomerTable();
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
    }

    public void cmbCustomerTypeOnAction(ActionEvent actionEvent) {
       String Cmb=cmbCustomerType.getValue();

       if (Cmb.equals("On-time")){
           txtCreditPeriod.setVisible(false);
           txtCreditLimit.setVisible(false);
           lblCreditPeriod.setVisible(false);
           lblcreditLimit.setVisible(false);
       }else{
           txtCreditPeriod.setVisible(true);
           txtCreditLimit.setVisible(true);
           lblCreditPeriod.setVisible(true);
           lblcreditLimit.setVisible(true);
       }
    }

    public void EmployeeSearchBtnOnAction(ActionEvent actionEvent) {

    }
}
