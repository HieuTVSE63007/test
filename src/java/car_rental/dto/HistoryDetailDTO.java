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
public class HistoryDetailDTO implements Serializable{
    private int historyID;
    private int carid;
    private String carName;
    private int quantity;
    private float unitPrice;

    public HistoryDetailDTO() {
    }

    public HistoryDetailDTO(int historyID, int carid, String carName, int quantity, float unitPrice) {
        this.historyID = historyID;
        this.carid = carid;
        this.carName = carName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    public int getCarid() {
        return carid;
    }

    public void setCarid(int carid) {
        this.carid = carid;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    
}
