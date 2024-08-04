package model;

import dto.Customer;
import dto.InvoiceDetails;
import dto.InvoiceMaster;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface InvoiceModel {
    boolean saveInvoice(InvoiceMaster ivm , InvoiceDetails ivd) throws SQLException, ClassNotFoundException;
    boolean updateInvoice(InvoiceMaster ivm , InvoiceDetails ivd) throws SQLException, ClassNotFoundException;
    boolean deleteInvoice(String id) throws SQLException, ClassNotFoundException;
    ResultSet getInvoiceById(String InvoNumber) throws SQLException, ClassNotFoundException;
    List<InvoiceMaster> AllInvoice();
}
