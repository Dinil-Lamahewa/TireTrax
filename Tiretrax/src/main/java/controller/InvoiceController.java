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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import db.DBConnection;
import model.InvoiceModel;
import model.impl.InvoiceModelImpl;
import javafx.print.PrinterJob;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        RemraksId.setText(":-");
        vatRateId.setText("0");
        String id = genereateInvoNo();
        InvoId.setText(id);
        loadCustomerData();
        loadItemData();
        setupTable();
        setupListeners();
        DateAndTimeId.setEditable(false);
        DateAndTimeId.setValue(LocalDate.from(LocalDateTime.now()));
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
            double netAmount = (grossAmount / 100) * vatRate+grossAmount;
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
        String sql = "SELECT Name,CustomerId FROM customer";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name")+"-"+resultSet.getString("CustomerId");
            customerList.add(name);
        }
        CustomerId.setItems(customerList);
    }

    private void loadItemData() throws SQLException, ClassNotFoundException {
        ObservableList<String> itemList = FXCollections.observableArrayList();
        String sql = "SELECT Name,ItemCode FROM stock";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name")+"-"+resultSet.getString("ItemCode");
            itemList.add(name);
        }
        ItemId.setItems(itemList);
    }

    public void ItemIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadItemData();
        String iid =ItemId.getValue();
        String[] parts1 = iid.split("-");
        String iiid = parts1[0];
        String iiid1 = parts1[1];
        String selectedItem = iiid;
        try {
            String sql = "SELECT SellingUnitPrice FROM stock WHERE Name = ?";
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1, selectedItem);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double unitPrice = resultSet.getDouble("SellingUnitPrice");
                InvoiceItem item = new InvoiceItem(iiid1,selectedItem, unitPrice);
                invoiceItems.add(item);
                CartTblId.refresh();
                updateAmounts();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void PrintIdbtnOnAction(ActionEvent actionEvent) {
        try {

            List<InvoiceDetails> invoiceDetailsList = new ArrayList<>();
            int seq = 1;
            InvoiceDetails invoiceDetails;
            for (InvoiceItem item : invoiceItems) {

                 invoiceDetails = new InvoiceDetails(
                        InvoId.getText(),  // Invoice number
                        seq++,             // Sequence number
                         item.getItemId(), // Item code (assuming item name is the item code)
                        item.getQuantity(),// Quantity
                        item.getUnitPrice()// Purchase order price
                );
                invoiceDetailsList.add(invoiceDetails);
            }
            String cid =CustomerId.getValue();
            String[] parts = cid.split("-");
            String customerId = parts[1];
            InvoiceMaster invoiceMaster = new InvoiceMaster(
                    InvoId.getText(),
                    customerId,
                    LocalDateTime.now(),
                    RemraksId.getText(),
                    Double.parseDouble(GrossAmountId.getText()),
                    Integer.parseInt(vatRateId.getText()),
                    Double.parseDouble(NetAmountId.getText()),
                    1
            );

            boolean isSaved = invoiceModel.saveInvoice(invoiceMaster, invoiceDetailsList);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Invoice Saved!").show();
                clearFields();
                // Generate HTML content and show print view
                String htmlContent = generateInvoiceHtml(invoiceMaster, invoiceDetailsList);
                showPrintView(htmlContent);
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println(""+ex);
            new Alert(Alert.AlertType.ERROR, "Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateInvoiceHtml(InvoiceMaster invoiceMaster, List<InvoiceDetails> invoiceDetailsList) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Invoice</title>");
        html.append("<style>")
                .append("body { font-family: Arial, sans-serif; }")
                .append(".header { display: flex; justify-content: space-between; align-items: center; }")
                .append(".header img { width: 100px; }")
                .append(".header .company-info { text-align: left; }")
                .append(".header .invoice-info { text-align: right; }")
                .append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }")
                .append("table, th, td { border: 1px solid black; }")
                .append("th, td { padding: 10px; text-align: left; }")
                .append("</style>")
                .append("</head><body>");

        html.append("<div class='header'>")
                .append("<div class='company-info'>")
                .append("<img src='src/main/resources/img/Tire.png' alt='Company Logo'>")
                .append("<h2>D.K. TIRE WORKS</h2>")
                .append("<p>Nuwaraeliya Roads, Thalawakele.</p>")
                .append("<p>Tel: 055-2258460, 0773846001, 0753846001</p>")
                .append("</div>")
                .append("<div class='invoice-info'>")
                .append("<p>Date: ").append(invoiceMaster.getInvoDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("</p>")
                .append("<p>Invoice #: ").append(invoiceMaster.getInvoNumber()).append("</p>")
                .append("</div>")
                .append("</div>");

        html.append("<h2>Invoice Details</h2>");
        html.append("<table>")
                .append("<tr><th>Item ID</th><th>Quantity</th><th>Unit Price</th><th>Total Price</th></tr>");
        for (InvoiceDetails detail : invoiceDetailsList) {
            html.append("<tr>")
                    .append("<td>").append(detail.getItemCode()).append("</td>")
                    .append("<td>").append(detail.getQty()).append("</td>")
                    .append("<td>").append(detail.getPoPrice()).append("</td>")
                    .append("<td>").append(detail.getQty() * detail.getPoPrice()).append("</td>")
                    .append("</tr>");
        }
        html.append("</table>");

        html.append("<p><strong>Gross Amount: </strong>").append(invoiceMaster.getGrossAmount()).append("</p>")
                .append("<p><strong>VAT Rate: </strong>").append(invoiceMaster.getVatRate()).append("</p>")
                .append("<p><strong>Net Amount: </strong>").append(invoiceMaster.getNetAmount()).append("</p>");

        html.append("</body></html>");
        return html.toString();
    }


    private void showPrintView(String htmlContent) {
        Stage stage = new Stage();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(htmlContent);

        Scene scene = new Scene(webView, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Print Preview");
        stage.show();

        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean proceed = printerJob.showPrintDialog(stage);
            if (proceed) {
                webEngine.print(printerJob);
                printerJob.endJob();
            }
        }
    }


    private void clearFields() throws SQLException, ClassNotFoundException {
        String id = genereateInvoNo();
        InvoId.setText(id);
        DateAndTimeId.setValue(null);
        RemraksId.setText("");
        GrossAmountId.setText("");

        vatRateId.setText("");
        NetAmountId.setText("");
        CustomerId.setValue(null);
        ObservableList<InvoiceItem> items = CartTblId.getItems();
        items.clear();
        GrossAmountId.clear();
        RemraksId.setText(":-");
        vatRateId.setText("0");
        DateAndTimeId.setValue(LocalDate.from(LocalDateTime.now()));
    }

    public void ReturnPOOnAction(ActionEvent actionEvent) {
        new Alert(Alert.AlertType.WARNING, "Under Maintenance").show();
    }

    public void BackBtnOnACTION(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) PrintId.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeDashBoard.fxml"))));
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
