package Model.impl;

import db.DBConnection;
import dto.SystemUsers;
import model.SystemUsersModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SystemUserModel implements SystemUsersModel {
    @Override
    public boolean saveSystemUsers(SystemUsers systemUsers) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO SystemUsers VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, systemUsers.getUserID());
        preparedStatement.setString(2, systemUsers.getUserName());
        preparedStatement.setString(3,systemUsers.getPassword());
        preparedStatement.setInt(4, systemUsers.getJobRole());
        preparedStatement.setDate(5, (Date) systemUsers.getLastLoginDate());

        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean updateSystemUsers(SystemUsers systemUsers) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE SystemUsers SET  userName = ?, password = ?, jobRole=?, last_login_date=? WHERE userID=?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, systemUsers.getUserName());
        preparedStatement.setString(2,systemUsers.getPassword());
        preparedStatement.setInt(3, systemUsers.getJobRole());
        preparedStatement.setDate(4, (Date) systemUsers.getLastLoginDate());
        preparedStatement.setString(5, systemUsers.getUserID());

        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public boolean deleteSystemUsers(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM SystemUsers WHERE userID = ?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,id);
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public List<SystemUsers> allSystemUsers() {
        return null;
    }
}
