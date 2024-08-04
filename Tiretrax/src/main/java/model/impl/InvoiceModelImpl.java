package model.impl;

import db.DBConnection;
import dto.InvoiceDetails;
import dto.InvoiceMaster;
import model.InvoiceModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InvoiceModelImpl implements InvoiceModel {
    @Override
    public boolean saveInvoice(InvoiceMaster ivm, InvoiceDetails ivd) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO invoicemaster VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, ivm.getInvoNumber());
        preparedStatement.setString(2, ivm.getCustomerId());
        preparedStatement.setDate(3, (Date) ivm.getInvoDate());
        preparedStatement.setString(4, ivm.getInvoComment());
        preparedStatement.setDouble(5, ivm.getGrossAmount());
        preparedStatement.setInt(6, ivm.getVatRate());
        preparedStatement.setDouble(7,ivm.getNetAmount());
        preparedStatement.setInt(8, ivm.getPrintCount());

        String sql2 = "INSERT INTO invoicedetails VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement1 = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement1.setString(1, ivd.getInvoNumber());
        preparedStatement1.setInt(2, 1);
        preparedStatement1.setString(3, ivd.getItemCode());
        preparedStatement1.setInt(4, ivd.getQty());
        preparedStatement1.setDouble(5,ivd.getPoPrice());
        return preparedStatement.executeUpdate()>0 && preparedStatement1.executeUpdate()>0;
    }

    @Override
    public boolean updateInvoice(InvoiceMaster ivm, InvoiceDetails ivd) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean deleteInvoice(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ResultSet getInvoiceById(String InvoNumber) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<InvoiceMaster> AllInvoice() {
        return List.of();
    }
}
