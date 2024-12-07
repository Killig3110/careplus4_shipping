package com.careplus4.shipping.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "Package")
public class Package implements java.io.Serializable {

    @Id
    @Column(name = "ID", length = 7)
    @Size(max = 7)
    private String id;

    @Column(name = "IDBill", length = 7)
    @Size(max = 7)
    private String idBill;

    @Column(name = "IDShippingMethod", length = 7)
    @Size(max = 7)
    private String idShippingMethod;

    @Column(name = "ReceiverName", columnDefinition = "NVARCHAR(255)", nullable = false)
    @NotEmpty(message = "Receiver name is required")
    private String receiverName;

    @Column(name = "UserPhone", length = 10)
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String userPhone;

    @Column(name = "Address", columnDefinition = "NVARCHAR(255)", nullable = false)
    @NotEmpty(message = "Address is required")
    private String address;

    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private java.util.Date date;

    @Column(name = "Status", columnDefinition = "NVARCHAR(255)", nullable = false)
    @NotEmpty(message = "Status is required")
    private String status;

    @Column(name = "TotalAmount", precision = 10, scale = 2, nullable = false)
    @DecimalMin("0.01")
    private BigDecimal totalAmount;

    // Default constructor
    public Package() {
    }

    // Constructor with all fields
    public Package(String id, String idBill, String idShippingMethod, String receiverName,
                   String userPhone, String address, java.util.Date date,
                   String status, BigDecimal totalAmount) {
        this.id = id;
        this.idBill = idBill;
        this.idShippingMethod = idShippingMethod;
        this.receiverName = receiverName;
        this.userPhone = userPhone;
        this.address = address;
        this.date = date;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public Package(String idBill, String idShippingMethod, String receiverName,
                   String userPhone, String address, java.util.Date date,
                   String status, BigDecimal totalAmount) {
        this.idBill = idBill;
        this.idShippingMethod = idShippingMethod;
        this.receiverName = receiverName;
        this.userPhone = userPhone;
        this.address = address;
        this.date = date;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdShippingMethod() {
        return idShippingMethod;
    }

    public void setIdShippingMethod(String idShippingMethod) {
        this.idShippingMethod = idShippingMethod;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Optional: toString method for debugging
    @Override
    public String toString() {
        return "Package{" +
                "id='" + id + '\'' +
                ", idBill='" + idBill + '\'' +
                ", idShippingMethod='" + idShippingMethod + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
