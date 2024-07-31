package model;

import dto.Stock;

import java.util.List;

public interface StockModel {
    boolean saveStock(Stock stock);
    boolean updateStock(Stock stock);
    boolean deleteStock(String id);
    List<Stock> allStock();
}
