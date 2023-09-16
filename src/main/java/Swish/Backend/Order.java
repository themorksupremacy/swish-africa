package Swish.Backend;

import java.sql.Date;

public class Order {

    //private fields
    private int orderID;
    private int userID;
    private int addressID;
    private Date date;
    private double cost;
    private int numItems;

    //parameterised constructor
    public Order(int orderID, int userID, int addressID, Date date, double cost, int numItems) {
        this.orderID = orderID;
        this.userID = userID;
        this.addressID = addressID;
        this.date = date;
        this.cost = cost;
        this.numItems = numItems;
    }

    //getter
    public int getOrderID() {
        return orderID;
    }

    //setter
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    //getter
    public int getUserID() {
        return userID;
    }

    //setter
    public void setUserID(int userID) {
        this.userID = userID;
    }

    //getter
    public int getAddressID() {
        return addressID;
    }

    //setter
    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    //getter
    public Date getDate() {
        return date;
    }

    //setter
    public void setDate(Date date) {
        this.date = date;
    }

    //getter
    public double getCost() {
        return cost;
    }

    //setter
    public void setCost(double cost) {
        this.cost = cost;
    }

    //toString method for the order fields
    public String toString(){
        return "Dated Ordered: " + this.date + " | Number of Products Ordered: " + this.numItems + " | Total Cost: R" + this.cost;
    }
}
