package model.impl;

import db.DBConnection;
import dto.InvoiceDetails;
import dto.InvoiceMaster;
import model.InvoiceModel;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class InvoiceModelImpl implements InvoiceModel {
    @Override
    public boolean saveInvoice(InvoiceMaster ivm, List<InvoiceDetails> ivdList) throws SQLException, ClassNotFoundException {
        String sqlMaster = "INSERT INTO invoicemaster (InvoNumber, CustomerId, InvoDate, InvoComment, GrossAmount, VatRate, NetAmount, PrintCount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatementMaster = DBConnection.getInstance().getConnection().prepareStatement(sqlMaster);

        preparedStatementMaster.setString(1, ivm.getInvoNumber());
        preparedStatementMaster.setString(2, ivm.getCustomerId());
        LocalDateTime dateTime = ivm.getInvoDate(); // Get LocalDateTime from ivm
        Timestamp timestamp = Timestamp.valueOf(dateTime); // Convert to Timestamp
        preparedStatementMaster.setTimestamp(3, timestamp);
        preparedStatementMaster.setString(4, ivm.getInvoComment());
        preparedStatementMaster.setDouble(5, ivm.getGrossAmount());
        preparedStatementMaster.setInt(6, ivm.getVatRate());
        preparedStatementMaster.setDouble(7, ivm.getNetAmount());
        preparedStatementMaster.setInt(8, ivm.getPrintCount());

        boolean isMasterSaved = preparedStatementMaster.executeUpdate() > 0;

        String sqlDetails = "INSERT INTO invoicedetails (InvoNumber, Seq, ItemCode, Qty, PoPrice) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatementDetails = DBConnection.getInstance().getConnection().prepareStatement(sqlDetails);

        for (InvoiceDetails ivd : ivdList) {
            preparedStatementDetails.setString(1, ivd.getInvoNumber());
            preparedStatementDetails.setInt(2, ivd.getSeq());
            preparedStatementDetails.setString(3, ivd.getItemCode());
            preparedStatementDetails.setInt(4, ivd.getQty());
            preparedStatementDetails.setDouble(5, ivd.getPoPrice());
            preparedStatementDetails.addBatch();
        }

        int[] detailResults = preparedStatementDetails.executeBatch();
        boolean isDetailsSaved = detailResults.length == ivdList.size() && Arrays.stream(detailResults).allMatch(result -> result > 0);

        return isMasterSaved && isDetailsSaved;
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
