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
public class CreateNewAccountErrorDTO implements Serializable{
    private String errorEmail;
    private String errorName;
    private String errorPassword;
    private String errorPhone;
    private String errorAddress;
    private String errorConfirmPassword;
    private String isExistAccount;

    public CreateNewAccountErrorDTO() {
    }

    public CreateNewAccountErrorDTO(String errorEmail, String errorName, String errorPassword, String errorPhone, String errorAddress, String errorConfirmPassword, String isExistAccount) {
        this.errorEmail = errorEmail;
        this.errorName = errorName;
        this.errorPassword = errorPassword;
        this.errorPhone = errorPhone;
        this.errorAddress = errorAddress;
        this.errorConfirmPassword = errorConfirmPassword;
        this.isExistAccount = isExistAccount;
    }

    /**
     * @return the errorEmail
     */
    public String getErrorEmail() {
        return errorEmail;
    }

    /**
     * @param errorEmail the errorEmail to set
     */
    public void setErrorEmail(String errorEmail) {
        this.errorEmail = errorEmail;
    }

    /**
     * @return the errorName
     */
    public String getErrorName() {
        return errorName;
    }

    /**
     * @param errorName the errorName to set
     */
    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    /**
     * @return the errorPassword
     */
    public String getErrorPassword() {
        return errorPassword;
    }

    /**
     * @param errorPassword the errorPassword to set
     */
    public void setErrorPassword(String errorPassword) {
        this.errorPassword = errorPassword;
    }

    /**
     * @return the errorPhone
     */
    public String getErrorPhone() {
        return errorPhone;
    }

    /**
     * @param errorPhone the errorPhone to set
     */
    public void setErrorPhone(String errorPhone) {
        this.errorPhone = errorPhone;
    }

    /**
     * @return the errorAddress
     */
    public String getErrorAddress() {
        return errorAddress;
    }

    /**
     * @param errorAddress the errorAddress to set
     */
    public void setErrorAddress(String errorAddress) {
        this.errorAddress = errorAddress;
    }

    /**
     * @return the errorConfirmPassword
     */
    public String getErrorConfirmPassword() {
        return errorConfirmPassword;
    }

    /**
     * @param errorConfirmPassword the errorConfirmPassword to set
     */
    public void setErrorConfirmPassword(String errorConfirmPassword) {
        this.errorConfirmPassword = errorConfirmPassword;
    }

    /**
     * @return the isExistAccount
     */
    public String getIsExistAccount() {
        return isExistAccount;
    }

    /**
     * @param isExistAccount the isExistAccount to set
     */
    public void setIsExistAccount(String isExistAccount) {
        this.isExistAccount = isExistAccount;
    }

    
}
