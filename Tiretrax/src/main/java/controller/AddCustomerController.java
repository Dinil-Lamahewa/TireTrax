package controller;

import db.DBConnection;
import dto.Customer;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.sql.*;



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
    public TableView<Customer> tblCustomer;
    public TableColumn<Customer, String> cusId;
    public TableColumn<Customer, String> name;
    public TableColumn<Customer, String> contact;
    public TableColumn<Customer, String> email;
    public TableColumn<Customer, String> address;
    public TableColumn<Customer, String> type;
    public TableColumn<Customer, Double> creditLimit;
    public TableColumn<Customer, String> creditPeriod;
    public TableColumn<Customer, Void> editColumn;
    public AnchorPane AddCustomer;
    public Button SearBtn;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    public void initialize() {

        refreshTable();
        // Initialize TableView columns
        cusId.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCustomerId()));
        name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        contact.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getContact()));
        email.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEmail()));
        address.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
        type.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getType()));
        creditLimit.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCreditLimit()));
        creditPeriod.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCreditPeriod()));

        cmbCustomerType.setItems(FXCollections.observableArrayList("Regular", "On-time"));

        // Add a cell factory to the editColumn
        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
                final TableCell<Customer, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Customer customer = getTableView().getItems().get(getIndex());
                            loadCustomerData(customer);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        editColumn.setCellFactory(cellFactory);

        // Add some dummy data for demonstration

    }

    private void refreshTable() {
        customerList.clear(); // Clear the current list

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customer");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("CustomerId");
                String name = resultSet.getString("Name");
                String contact = resultSet.getString("Contact");
                String email = resultSet.getString("Email");
                String address = resultSet.getString("Address");
                String type = resultSet.getString("Type");
                double creditLimit = resultSet.getDouble("CreditLimit");
                String creditPeriod = resultSet.getString("CreditPeriod");

                customerList.add(new Customer(id, name, contact, email, address, type, creditLimit, creditPeriod));
            }

            tblCustomer.setItems(customerList); // Set the updated list to TableView
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void loadCustomerData(Customer customer) {
        String[] nameParts = customer.getName().split(" ", 2);
        txtFirstName.setText(nameParts.length > 0 ? nameParts[0] : "");
        txtLastName.setText(nameParts.length > 1 ? nameParts[1] : "");
        txtAddress.setText(customer.getAddress());
        txtContact.setText(customer.getContact());
        txtEmail.setText(customer.getEmail());
        txtCreditLimit.setText(String.valueOf(customer.getCreditLimit()));
        txtCreditPeriod.setText(customer.getCreditPeriod());
        cmbCustomerType.setValue(customer.getType());
    }
    private String generateCustomerId() {
        String customerId = null;

        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT MAX(CustomerId) AS maxId FROM Customer")) {

            if (resultSet.next()) {
                String maxId = resultSet.getString("maxId");
                int idNumber = maxId != null ? Integer.parseInt(maxId.replace("CS", "")) : 0;
                customerId = String.format("CS%05d", idNumber + 1);
            } else {
                customerId = "CS00001";
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return customerId;
    }


    public void btnBackOnAction(ActionEvent actionEvent) {
        // Handle back button action
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String address = txtAddress.getText().trim();
        String contact = txtContact.getText().trim();
        String email = txtEmail.getText().trim();
        String creditPeriod = txtCreditPeriod.getText().trim();
        String type = cmbCustomerType.getValue();

        // Validate inputs
        if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() ||
                contact.isEmpty() || email.isEmpty() || creditPeriod.isEmpty() || type == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        double creditLimit;
        try {
            creditLimit = Double.parseDouble(txtCreditLimit.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Invalid credit limit.");
            return;
        }

        String customerId = generateCustomerId();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Customer (CustomerId, Name, Contact, Email, Address, Type, CreditLimit, CreditPeriod) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, customerId);
            statement.setString(2, firstName + " " + lastName);
            statement.setString(3, contact);
            statement.setString(4, email);
            statement.setString(5, address);
            statement.setString(6, type);
            statement.setDouble(7, creditLimit);
            statement.setString(8, creditPeriod);

            statement.executeUpdate();
            refreshTable(); // Refresh the TableView after saving
            clearFields();  // Clear the input fields
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Consider showing an error message to the user
            showAlert("Error saving customer data."+ System.err);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void SearBtnOnAction(ActionEvent actionEvent) throws SQLException {

    }
    private void clearFields() {
        txtFirstName.clear();
        txtLastName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtEmail.clear();
        txtCreditLimit.clear();
        txtCreditPeriod.clear();
        cmbCustomerType.setValue(null);
    }
}
