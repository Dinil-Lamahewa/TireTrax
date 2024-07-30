package model.impl;

import dto.Stock;
import model.StockModel;

import java.util.List;

public class StockModelImpl implements StockModel {
    @Override
    public boolean saveStock(Stock stock) {
        return false;
    }

    @Override
    public boolean updateStock(Stock stock) {
        return false;
    }

    @Override
    public boolean deleteStock(String id) {
        return false;
    }

    @Override
    public List<Stock> allStock() {
        return null;
    }
}
