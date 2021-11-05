/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental.dao;

import car_rental.dto.UserDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import car_rental.ultilities.DBUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Truong Van Hieu
 */
public class UserDAO implements Serializable {

    Connection conn = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public boolean createNewAccount(String email, String name, String password)
            throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_Account VALUES(?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                stm.setString(3, name);
                stm.setString(4, "User");
                stm.setString(5, "New");
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public String currenDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean createNewAccountDetail(String email, String name, String phone, String address)
            throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_AccountDetail VALUES(?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, name);
                stm.setString(3, phone);
                stm.setString(4, address);
                stm.setString(5, currenDate());
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public UserDTO checkLogin(String userID, String password)
            throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "SELECT userID, password, fullName, role, status FROM tbl_Account WHERE userID = ? and password = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String email = rs.getString("userID");
                    String pass = rs.getString("password");
                    String fullname = rs.getString("fullName");
                    String role = rs.getString("role");
                    String status = rs.getString("status");
                    UserDTO dto = new UserDTO(email, pass, fullname, role, status);
                    return dto;
                }
            }
        } finally {
            closeConnection();
        }

        return null;
    }

    public String getUserName(String userID) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "SELECT fullName FROM tbl_Account WHERE userID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getString("Name");
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public boolean updateStatusAccount(String userID) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tbl_Account SET status = 'Active' WHERE userID = ? ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
}
