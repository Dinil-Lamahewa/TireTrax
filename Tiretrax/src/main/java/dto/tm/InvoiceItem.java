package dto.tm;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class InvoiceItem {
    private final StringProperty itemId;
    private final StringProperty itemName;
    private final DoubleProperty unitPrice;
    private final IntegerProperty quantity;
    private final DoubleProperty totalPrice;

    public InvoiceItem(String itemId, String itemName, double unitPrice) {
        this.itemId = new SimpleStringProperty(itemId);
        this.itemName = new SimpleStringProperty(itemName);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);
        this.quantity = new SimpleIntegerProperty(1);
        this.totalPrice = new SimpleDoubleProperty(unitPrice);

        this.unitPrice.addListener((obs, oldVal, newVal) -> updateTotalPrice());
        this.quantity.addListener((obs, oldVal, newVal) -> updateTotalPrice());
    }

    private void updateTotalPrice() {
        double unitPriceValue = unitPrice.get();
        int quantityValue = quantity.get();
        totalPrice.set(unitPriceValue * quantityValue);
    }

    public String getItemId() {
        return itemId.get();
    }

    public void setItemId(String itemId) {
        this.itemId.set(itemId);
    }

    public StringProperty itemIdProperty() {
        return itemId;
    }

    public String getItemName() {
        return itemName.get();
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }
}
