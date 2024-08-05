package Model;


import dto.Customer;
import dto.EmployeeUpdateCustomer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean saveCustomer(Customer customer) throws SQLException, ClassNotFoundException;
    boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    ResultSet getCustomerByContact(String contact) throws SQLException, ClassNotFoundException;
    List<Customer> allCustomers();
    boolean employeeUpdateCustomer(EmployeeUpdateCustomer customer) throws SQLException, ClassNotFoundException;

}
