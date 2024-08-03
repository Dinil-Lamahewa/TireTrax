package Model.impl;

import db.DBConnection;
import dto.Stock;
import model.StockModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StockModelImpl implements StockModel {
    @Override
    public boolean saveStock(Stock stock) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO Stock VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, stock.getItemCode());
        preparedStatement.setString(2, stock.getName());
        preparedStatement.setString(3, stock.getCategory());
        preparedStatement.setString(4, stock.getCompany());
        preparedStatement.setInt(5, Integer.parseInt(stock.getPurchaseQty()));
        preparedStatement.setString(6, "");
        preparedStatement.setDate(7, (Date) stock.getExpiredDate());
        preparedStatement.setDouble(8, Double.parseDouble((stock.getSellingUnitPrice())));
        preparedStatement.setDate(9, (Date) stock.getPurchaseDate());
        preparedStatement.setDouble(10, Double.parseDouble((stock.getPurchasePrice())));

        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean updateStock(Stock stock) {
        return false;
    }

    @Override
    public boolean deleteStock(String id) throws SQLException, ClassNotFoundException {

        String sql ="DELETE FROM Customer WHERE CustomerId=?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1,id);

        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public List<Stock> allStock() {
        return null;
    }
}
