package Model.impl;

import Model.CustomerModel;
import db.DBConnection;
import dto.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerModelImpl implements CustomerModel {
    @Override
    public boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO Customer VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, customer.getCustomerId());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setString(3, customer.getContact());
        preparedStatement.setString(4, customer.getEmail());
        preparedStatement.setString(5, customer.getAddress());
        preparedStatement.setString(6, customer.getType());
        preparedStatement.setDouble(7,customer.getCreditLimit());
        preparedStatement.setString(8, customer.getCreditPeriod());

        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        String sql ="UPDATE Customer SET Name=? , Contact = ?, Email = ?, Address = ?, Type = ?, CreditLimit = ?, CreditPeriod = ? WHERE CustomerId=?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getContact());
        preparedStatement.setString(3, customer.getEmail());
        preparedStatement.setString(4, customer.getAddress());
        preparedStatement.setString(5, customer.getType());
        preparedStatement.setDouble(6,customer.getCreditLimit());
        preparedStatement.setString(7, customer.getCreditPeriod());
        preparedStatement.setString(8, customer.getCustomerId());

        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        String sql ="DELETE FROM Customer WHERE CustomerId=?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1,id);

        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public ResultSet getCustomerByContact(String contact) throws SQLException, ClassNotFoundException {
        String sql ="SELECT*FROM Customer WHERE Contact=?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,contact);
        return preparedStatement.executeQuery();
    }

    @Override
    public List<Customer> allCustomers() {
        return null;
    }
}
