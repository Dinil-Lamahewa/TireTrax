package Model;

import dto.Stock;

import java.sql.SQLException;
import java.util.List;

public interface StockModel {
    boolean saveStock(Stock stock) throws SQLException, ClassNotFoundException;
    boolean updateStock(Stock stock);
    boolean deleteStock(String id) throws SQLException, ClassNotFoundException;
    List<Stock> allStock();
}
