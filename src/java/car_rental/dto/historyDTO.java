/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package car_rental.dto;

import java.io.Serializable;

/**
 *
 * @author Truong Van Hieu
 */
public class historyDTO implements Serializable{
    private int historyID;
    private String userID;
    private float totalPrice;
    private String status;
    private String orderDate;

    public historyDTO() {
    }

    public historyDTO(int historyID, String userID, float totalPrice, String status, String orderDate) {
        this.historyID = historyID;
        this.userID = userID;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
    }

    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    
}
