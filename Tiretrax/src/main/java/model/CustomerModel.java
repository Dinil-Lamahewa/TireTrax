package model;


import dto.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    List<Customer> allCustomers();
}
