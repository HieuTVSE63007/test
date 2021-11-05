/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental.dao;

import car_rental.dto.CarDTO;
import car_rental.dto.CategoryDTO;
import car_rental.dto.HistoryDetailDTO;
import car_rental.dto.historyDTO;
import car_rental.ultilities.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Truong Van Hieu
 */
public class CarDAO implements Serializable {

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
    private List<CategoryDTO> listCategory;

    public List<CategoryDTO> getListCategory() {
        return listCategory;
    }

    public void searchListCategory() throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            String sql = "SELECT CategoryID, CategoryName FROM tbl_Category";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                String categoryID = rs.getString("CategoryID");
                String categoryName = rs.getString("CategoryName");
                CategoryDTO dto = new CategoryDTO(categoryID, categoryName);
                if (this.listCategory == null) {
                    listCategory = new ArrayList<>();
                }
                listCategory.add(dto);
            }
        } finally {
            closeConnection();
        }
    }
    private List<CarDTO> listCar;

    public List<CarDTO> getListCar() {
        return listCar;
    }

    public void searchCar(String carName, String category, String rentalDate, String returnDate, int quantity) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "SELECT carID, carName, color, year , category, price, quantity "
                        + "FROM tbl_Car "
                        + "WHERE carName like ? and category like ? and rental_date like ? and return_date like ? and quantity BETWEEN 0 AND ? "
                        + "ORDER BY tbl_Car.rental_date DESC";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "%" + carName + "%");
                stm.setString(2, "%" + category + "%");
                stm.setString(3, "%" + rentalDate + "%");
                stm.setString(4, "%" + returnDate + "%");
                stm.setInt(5, quantity);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int carID = rs.getInt("carID");
                    String carname = rs.getString("carName");
                    String color = rs.getString("color");
                    String year = rs.getString("year");
                    String carCategory = rs.getString("category");
                    float price = rs.getFloat("price");
                    int carQuantity = rs.getInt("quantity");
                    CarDTO dto = new CarDTO(carID, carname, color, year, carCategory, price, carQuantity);
                    if (this.listCar == null) {
                        listCar = new ArrayList<>();
                    }
                    listCar.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
    }

    public int checkQuantity(int id) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "SELECT quantity FROM tbl_Car WHERE carID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        } finally {
            closeConnection();
        }
        return -1;
    }

    public String currenDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean checkOut(String userID, float totalPrice, String discountCode) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_History VALUES(?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setFloat(2, totalPrice);
                stm.setString(3, "Activate");
                stm.setString(4, currenDate());
                stm.setString(5, discountCode);
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

    public boolean checkOutDetail(int carID, String carName, int historyID,
            int quantity, float unitPrice) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_HistoryDetail VALUES(?,?,?,?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, carID);
                stm.setString(2, carName);
                stm.setInt(3, historyID);
                stm.setInt(4, quantity);
                stm.setFloat(5, unitPrice);
                stm.setString(6, currenDate());
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

    public int getHistoryID() throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "select MAX(historyID) as 'id' from tbl_History";
                stm = conn.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } finally {
            closeConnection();
        }
        return -1;
    }
    private List<historyDTO> listHistory;

    public List<historyDTO> getListHistory() {
        return listHistory;
    }

    public void searchHistory(String name, String orderDate) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "select historyID, userID, totalPrice, status, orderDate "
                        + "from tbl_History "
                        + "where userID = ? and orderDate like ? "
                        + "order by tbl_History.orderDate DESC ";
                stm = conn.prepareStatement(sql);
                stm.setString(1, name);
                stm.setString(2, "%" + orderDate + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    int history = rs.getInt("historyID");
                    String userid = rs.getString("userID");
                    float totalPrice = rs.getFloat("totalPrice");
                    String status = rs.getString("status");
                    String date = rs.getString("orderDate");
                    historyDTO dto = new historyDTO(history, userid, totalPrice, status, date);
                    if (this.listHistory == null) {
                        this.listHistory = new ArrayList<>();
                    }
                    listHistory.add(dto);
                }
            }

        } finally {
            closeConnection();
        }
    }

    public boolean deleteHistory(int historyID) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tbl_History SET status = 'Inactivate' WHERE historyID = ?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, historyID);
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

    public String getDiscount(String discount) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "select discountCode "
                        + "from tbl_Discount "
                        + "where discountCode = ? AND expiryDate BETWEEN ? AND expiryDate";
                stm = conn.prepareStatement(sql);
                stm.setString(1, discount);
                stm.setString(2, currenDate());
                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getString("discountCode");
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public boolean feedBack(int rating, String feedback, int histoyID) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "INSERT INTO tbl_Feedback VALUES(?,?,?)";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, rating);
                stm.setString(2, feedback);
                stm.setInt(3, histoyID);
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
    private List<HistoryDetailDTO> listHisDetail;

    public List<HistoryDetailDTO> getListHisDetail() {
        return listHisDetail;
    }

    public void getHistoryDetail(int historyID) throws NamingException, SQLException {
        try {
            conn = DBUtils.makeConnection();
            if (conn != null) {
                String sql = "SELECT historyDetailID, carID, carName, quantity, unitPrice "
                        + "FROM tbl_HistoryDetail "
                        + "WHERE historyID = ? ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, historyID);
                rs = stm.executeQuery();
                while(rs.next()){
                    int hisID = rs.getInt("historyDetailID");
                    int carID = rs.getInt("carID");
                    String carName = rs.getString("carName");
                    int quantity = rs.getInt("quantity");
                    float unitPrice = rs.getFloat("unitPrice");
                    HistoryDetailDTO dto = new HistoryDetailDTO(hisID, carID, carName, quantity, unitPrice);
                    if (this.listHisDetail == null){
                        this.listHisDetail = new ArrayList<>();
                    }
                    listHisDetail.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
    }
}
