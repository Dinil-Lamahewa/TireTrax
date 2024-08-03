package model;

import dto.Stock;
import dto.SystemUsers;

import java.sql.SQLException;
import java.util.List;

public interface SystemUsersModel {
    boolean saveSystemUsers(SystemUsers systemUsers) throws SQLException, ClassNotFoundException;
    boolean updateSystemUsers(SystemUsers systemUsers) throws SQLException, ClassNotFoundException;
    boolean deleteSystemUsers(String id) throws SQLException, ClassNotFoundException;
    List<SystemUsers> allSystemUsers();
}
